package com.ftn.sbnz.service.services.donosenje_odluke_o_trombolizi;

import com.ftn.sbnz.model.dto.request.NIHHSRequest;
import com.ftn.sbnz.model.dto.request.NastanakSimptomaRequest;
import com.ftn.sbnz.model.dto.request.NeuroloskiPregledRequest;
import com.ftn.sbnz.model.events.OdlukaOTromboliziEvent;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.service.repository.OdlukaOTromboliziRepository;
import com.ftn.sbnz.service.repository.KorisnikRepository;
import com.ftn.sbnz.service.services.alarm.AlarmListener;
import com.ftn.sbnz.service.services.bolesti.BolestiService;
import com.ftn.sbnz.service.services.korisnik.PacijentService;
import com.ftn.sbnz.service.services.mail.SendMail;
import com.ftn.sbnz.service.simulation.CTSimulacija;
import com.ftn.sbnz.service.simulation.LaboratorijaSimulacija;
import com.ftn.sbnz.service.simulation.MonitoringSimulacija;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OdlukaOTromboliziService {

    @Autowired
    public OdlukaOTromboliziService(final KieSession kieSession, Map<String, Pacijent> PacijentiNaEKGu, final KorisnikRepository korisnikRepository, final SendMail sendMail, final OdlukaOTromboliziRepository odlukaOTromboliziRepository, final PacijentService korisnikService, final MonitoringSimulacija monitoringSimulacija, final BolestiService bolestiService) {
        this.kieSession = kieSession;
        this.odlukaOTromboliziRepository = odlukaOTromboliziRepository;
        this.korisnikService = korisnikService;
        this.monitoringSimulacija = monitoringSimulacija;
        this.laboratorijaSimulacija = new LaboratorijaSimulacija();
        this.ctSimulacija = new CTSimulacija();
        this.sendMail = sendMail;
        this.korisnikRepository = korisnikRepository;
        this.PacijentiNaEKGu = PacijentiNaEKGu;
        this.bolestiService = bolestiService;
        OdlukaOTromboliziEventListener eventListener = new OdlukaOTromboliziEventListener(this);
        AlarmListener alarmListener = new AlarmListener(this.sendMail, this.korisnikRepository);
        kieSession.addEventListener(eventListener);
        kieSession.addEventListener(alarmListener);
    }

    private final KieSession kieSession;

    private final OdlukaOTromboliziRepository odlukaOTromboliziRepository;

    private final PacijentService korisnikService;

    private final CTSimulacija ctSimulacija;

    private final LaboratorijaSimulacija laboratorijaSimulacija;

    private final MonitoringSimulacija monitoringSimulacija;
    private final SendMail sendMail;
    private final KorisnikRepository korisnikRepository;
    private final Map<String, Pacijent> PacijentiNaEKGu;

    private final BolestiService bolestiService;


    public Odluka proveriOdlukuNaOsnovuNastankaSimptoma(final NastanakSimptomaRequest nastanakSimptomaRequest) {
        Pacijent pacijent = korisnikService.getPacijentByJmbg(nastanakSimptomaRequest.getJmbgPacijenta());
        PacijentiNaEKGu.putIfAbsent(pacijent.getJmbg(), pacijent);

        final Simptomi simptomi = new Simptomi(nastanakSimptomaRequest.getTrenutakNastanka(), nastanakSimptomaRequest.getStanjeSvesti(),
                nastanakSimptomaRequest.isNastaliUTokuSna());
        Odluka odluka = new Odluka(pacijent, StatusOdluke.PRIHVACENA_FAZA_1);
        odlukaOTromboliziRepository.save(odluka);

        proveriOdlukuNaOsnovuPrethodnihBolesti(pacijent.getJmbg(), odluka.getId());

        odluka = odlukaOTromboliziRepository.findOdlukaById(odluka.getId());
        if (!odluka.getStatus().equals(StatusOdluke.ODBIJENA)) {
            final OdlukaOTromboliziEvent odlukaEvent = new OdlukaOTromboliziEvent(odluka.getId(), StatusOdluke.PRIHVACENA_FAZA_1, simptomi, nastanakSimptomaRequest.isPostojeSvedoci());
            kieSession.insert(odlukaEvent);
            kieSession.fireAllRules();
        }
        return odluka;
    }

    public void proveriOdlukuNaOsnovuPrethodnihBolesti(String jmbgPacijenta, UUID idOdluke) {
        final OdlukaOTromboliziEvent odlukaEvent = new OdlukaOTromboliziEvent(idOdluke,
                StatusOdluke.PRIHVACENA_NA_OSNOVU_BOLESTI, bolestiService.kreirajKontraindikacijeOdPrethodnihBolestiPacijenta(jmbgPacijenta));
        kieSession.insert(odlukaEvent);
        kieSession.fireAllRules();
    }

    public Odluka proveriOdlukuNaOsnovuNeuroloskogPregleda(final NeuroloskiPregledRequest neuroloskiPregled) {
        final OdlukaOTromboliziEvent odlukaEvent = new OdlukaOTromboliziEvent(neuroloskiPregled.getIdOdluke(),
                StatusOdluke.PRIHVACENA_FAZA_2,
                neuroloskiPregled.kreirajKontraindikacije());
        Odluka odluka = izmeniStatusOdluke(neuroloskiPregled.getIdOdluke(), StatusOdluke.PRIHVACENA_FAZA_2);
        kieSession.insert(odlukaEvent);
        kieSession.fireAllRules();
        return odluka;
    }

    public Odluka proveriOdlukuNaOsnovuNIHHSSkora(final NIHHSRequest nihhsRequest) {
        final Integer skor = izracunajNIHHSSkor(nihhsRequest);
        final NIHHS nihhs = new NIHHS(skor, nihhsRequest.getJmbgPacijenta(), nihhsRequest.getIdOdluke(), new Date());
        kieSession.insert(nihhs);
        kieSession.fireAllRules();
        Odluka odluka = odlukaOTromboliziRepository.findOdlukaById(nihhsRequest.getIdOdluke());
        if (odluka.getStatus().equals(StatusOdluke.PRIHVACENA_FAZA_6)) odluka.setStatus(StatusOdluke.PRIHVACENA);
        PacijentiNaEKGu.remove(nihhsRequest.getJmbgPacijenta());
        return odlukaOTromboliziRepository.save(odluka);
    }

    public Integer izracunajNIHHSSkor(final NIHHSRequest nihhsRequest) {
        Integer sum = 0;

        sum += nihhsRequest.getStanjeSvesti();
        sum += nihhsRequest.getStanjeSvestiPitanja();
        sum += nihhsRequest.getStanjeSvestiNalozi();
        sum += nihhsRequest.getPokretiBulbusa();
        sum += nihhsRequest.getSirinaVidnogPolja();
        sum += nihhsRequest.getMimicnaPareza();
        sum += nihhsRequest.getMotorikaLevaRuka();
        sum += nihhsRequest.getMotorikaDesnaRuka();
        sum += nihhsRequest.getMotorikaLevaNoga();
        sum += nihhsRequest.getMotorikaDesnaNoga();
        sum += nihhsRequest.getAtaksijaEkstremiteta();
        sum += nihhsRequest.getSenzibilitet();
        sum += nihhsRequest.getGovor();
        sum += nihhsRequest.getDizartrija();
        sum += nihhsRequest.getFenomenNeglekta();

        return sum;
    }

    public void simulirajMerenjePritiska(final Odluka odluka) {
        final Pritisak pritisak = monitoringSimulacija.simulirajMerenjePritiska();
        System.out.println("Sistolni pritisak: " + pritisak.getSistolni());
        System.out.println("Dijastolni pritisak: " + pritisak.getDijastolni());
//        pritisak.setDijastolni(100);
//        pritisak.setSistolni(120);

        final OdlukaOTromboliziEvent odlukaEvent = new OdlukaOTromboliziEvent(odluka.getId(),
                StatusOdluke.PRIHVACENA_FAZA_3, pritisak);
        kieSession.insert(odlukaEvent);
        kieSession.fireAllRules();
    }

    public Odluka izmeniStatusOdluke(final UUID idOdluke, StatusOdluke noviStatusOdluke) {
        final Odluka odluka = odlukaOTromboliziRepository.findOdlukaById(idOdluke);
        if (noviStatusOdluke.equals(StatusOdluke.PRIHVACENA_NA_OSNOVU_BOLESTI))
            noviStatusOdluke = StatusOdluke.PRIHVACENA_FAZA_1;
        odluka.setStatus(noviStatusOdluke);
        return odlukaOTromboliziRepository.save(odluka);
    }

    public void simulirajCT(final Odluka izmenjenaOdluka) {
        final List<ZnakIshemije> znaci = ctSimulacija.simulirajOcitavanjeCTa();
        //final List<ZnakIshemije> znaci = Arrays.asList(ZnakIshemije.GUBITAK_GRANICE_IZMEDJU_BELE_I_SIVE_MASE, ZnakIshemije.HIPOATENUACIJA_BAZALNIH_GANGLIJA);
        System.out.println("Znaci ishemije: " + znaci);
        final OdlukaOTromboliziEvent odlukaEvent = new OdlukaOTromboliziEvent(izmenjenaOdluka.getId(), izmenjenaOdluka.getPacijent().getJmbg(), StatusOdluke.PRIHVACENA_FAZA_5, znaci);
        kieSession.insert(odlukaEvent);
        kieSession.fireAllRules();
    }

    public void simulirajLaboratoriju(final Odluka odluka) {
        final Laboratorija laboratorija = laboratorijaSimulacija.simulirajLaboratoriju();
        laboratorija.setTrombociti(110000.00);
        laboratorija.setGlikemija(20.00);
        laboratorija.setINR(1.65);
        System.out.println(laboratorija);
        final OdlukaOTromboliziEvent odlukaEvent = new OdlukaOTromboliziEvent(odluka.getId(),
                StatusOdluke.PRIHVACENA_FAZA_6, laboratorija);
        izmeniStatusOdluke(odluka.getId(), StatusOdluke.PRIHVACENA_FAZA_6);
        kieSession.insert(odlukaEvent);
        kieSession.fireAllRules();
    }

    @Transactional(readOnly = true)
    public List<Odluka> dobaviOdluke() {
        return odlukaOTromboliziRepository.findAllByStatusInOrderByCreatedAtDesc(List.of(StatusOdluke.PRIHVACENA, StatusOdluke.ODBIJENA));
    }
}
