package com.ftn.sbnz.service.services.procena_rizika_od_MU;

import com.ftn.sbnz.model.dto.request.ProcenaRizikaOdMURequest;
import com.ftn.sbnz.model.events.ProcenaRizikaOdMUEvent;
import com.ftn.sbnz.model.models.NivoRizikaOdMU;
import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.model.models.Pritisak;
import com.ftn.sbnz.model.models.ProcenaRizikaOdMU;
import com.ftn.sbnz.service.repository.ProcenaRizikaOdMURepository;
import com.ftn.sbnz.service.services.korisnik.PacijentService;
import com.ftn.sbnz.service.simulation.MonitoringSimulacija;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ProcenaRizikaOdMUService {

    private final PacijentService korisnikService;
    private final KieSession kieSession;
    private final ProcenaRizikaOdMURepository procenaRizikaOdMURepository;
    private final Map<String, Pacijent> PacijentiNaEKGu;
    private final MonitoringSimulacija monitoringSimulacija;

    @Autowired
    public ProcenaRizikaOdMUService(PacijentService korisnikService, KieSession kieSession, ProcenaRizikaOdMURepository procenaRizikaOdMURepository, Map<String, Pacijent> pacijentiNaEKGu, MonitoringSimulacija monitoringSimulacija) {
        this.korisnikService = korisnikService;
        this.kieSession = kieSession;
        this.procenaRizikaOdMURepository = procenaRizikaOdMURepository;
        PacijentiNaEKGu = pacijentiNaEKGu;
        this.monitoringSimulacija = monitoringSimulacija;
        kieSession.addEventListener(new ProcenaRizikaOdMUEventListener(this, PacijentiNaEKGu));
    }

    @Transactional(readOnly = true)
    public List<ProcenaRizikaOdMU> getAll() {
        return procenaRizikaOdMURepository.findAllByOrderByCreatedAtDesc();
    }

    public NivoRizikaOdMU utvrdiNivoRizika(final ProcenaRizikaOdMURequest procenaRizikaOdMURequest) throws IOException {
        Pacijent pacijent = korisnikService.getPacijentByJmbg(procenaRizikaOdMURequest.getJmbgPacijenta());
        PacijentiNaEKGu.putIfAbsent(pacijent.getJmbg(), pacijent);

        final Integer ABCD2Skor = izracunajABCD2Skor(procenaRizikaOdMURequest, pacijent);

        System.out.println("ABCD2 skor: " + ABCD2Skor);
        ProcenaRizikaOdMU procenaRizika = new ProcenaRizikaOdMU(pacijent, NivoRizikaOdMU.PROCENA_U_TOKU);
        procenaRizika = procenaRizikaOdMURepository.save(procenaRizika);

        final ProcenaRizikaOdMUEvent procenaRizikaEvent = new ProcenaRizikaOdMUEvent(procenaRizika.getId(), pacijent.getJmbg(), NivoRizikaOdMU.PROCENA_U_TOKU, ABCD2Skor, procenaRizikaOdMURequest.getStenozaSimptomatskogKrvnogSuda());
        kieSession.insert(procenaRizika.getId());
        kieSession.insert(procenaRizikaEvent);
        kieSession.fireAllRules();

        ProcenaRizikaOdMU izmenjenaProcena = procenaRizikaOdMURepository.findById(procenaRizika.getId()).get();
        NivoRizikaOdMU nivoRizika = izmenjenaProcena.getNivoRizika();
        if (nivoRizika.equals(NivoRizikaOdMU.PROCENA_U_TOKU)) {
            kieSession.insert(new ProcenaRizikaOdMUEvent(izmenjenaProcena.getId(), izmenjenaProcena.getPacijent().getJmbg(), NivoRizikaOdMU.PROCENA_RIZIKA_OD_ATRIJALNE_FIBRILACIJE));
            kieSession.fireAllRules();
        }

        nivoRizika = dobaviKonacniNivoRizika(izmenjenaProcena);
        PacijentiNaEKGu.remove(pacijent.getJmbg());

        //brisanje zbog pravila: TIA unutar tri dana
        FactHandle factHandle = kieSession.getFactHandle(procenaRizika.getId());
        kieSession.delete(factHandle);

        return nivoRizika;
    }


    public NivoRizikaOdMU dobaviKonacniNivoRizika(ProcenaRizikaOdMU procena) {
        if (procena.getNivoRizika().equals(NivoRizikaOdMU.PROCENA_U_TOKU)) {
            procena.setNivoRizika(NivoRizikaOdMU.NIZAK_RIZIK);
            procenaRizikaOdMURepository.save(procena);
        }
        return procena.getNivoRizika();
    }

    public Integer izracunajABCD2Skor(final ProcenaRizikaOdMURequest procenaRizikaOdMURequest, final Pacijent pacijent) {
        int skor = 0;
        final Pritisak pritisak = monitoringSimulacija.simulirajMerenjePritiska();
        if ((pritisak.getSistolni() > 140) || (pritisak.getDijastolni() > 90)) skor += 1;
        int razlika = pacijent.dobaviGodine();
        if (razlika > 60) skor += 1;
        if (procenaRizikaOdMURequest.isHemipareza() || procenaRizikaOdMURequest.isHemiplegija()) skor += 2;
        if (procenaRizikaOdMURequest.isSmetnjeGovora()) skor += 1;
        if (procenaRizikaOdMURequest.getTrajanjeSimptoma() >= 60) skor += 2;
        else if (procenaRizikaOdMURequest.getTrajanjeSimptoma() > 10) skor += 1;
        if (procenaRizikaOdMURequest.isDijabetes()) skor += 1;
        return skor;
    }

    public ProcenaRizikaOdMU izmeniNivoRizika(final UUID idOdluke, final NivoRizikaOdMU noviNivoRizika) {
        final ProcenaRizikaOdMU procenaRizika = procenaRizikaOdMURepository.findById(idOdluke).get();
        procenaRizika.setNivoRizika(noviNivoRizika);
        procenaRizikaOdMURepository.save(procenaRizika);
        return procenaRizika;
    }

}
