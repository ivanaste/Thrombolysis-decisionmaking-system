package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.dto.request.TIARequest;
import com.ftn.sbnz.model.events.ProcenaRizikaOdMUEvent;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.service.repository.ProcenaRizikaOdMURepository;
import com.ftn.sbnz.service.simulation.MonitoringSimulacija;

import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcenaRizikaOdMUService {
	private final KorisnikService korisnikService;
	private final KieSession kieSession;
	private final ProcenaRizikaOdMURepository procenaRizikaOdMURepository;

	private final MonitoringSimulacija monitoringSimulacija = new MonitoringSimulacija();
	public String utvrdiNivoRizikaOdMU(final TIARequest tiaRequest) {
		final Pacijent pacijent = korisnikService.getPacijentByJmbg(tiaRequest.getJmbgPacijenta());
		final Integer ABCD2Skor = izracunajABCD2Skor(tiaRequest, pacijent);
		ProcenaRizikaOdMU procenaRizika = new ProcenaRizikaOdMU(pacijent, NivoRizikaOdMU.PROCENA_U_TOKU);
		procenaRizika = procenaRizikaOdMURepository.save(procenaRizika);

		final ProcenaRizikaOdMUEvent procenaRizikaEvent = new ProcenaRizikaOdMUEvent(procenaRizika.getId(), NivoRizikaOdMU.PROCENA_U_TOKU, ABCD2Skor, tiaRequest.getStenozaSimptomatskogKrvnogSuda());
		kieSession.addEventListener(new ProcenaRizikaOdMUEventListener(this));
		kieSession.insert(procenaRizikaEvent);
		kieSession.fireAllRules();
		return procenaRizikaOdMURepository.getReferenceById(procenaRizika.getId()).getNivoRizika().name();
	}

	public Integer izracunajABCD2Skor(final TIARequest tiaRequest, final Pacijent pacijent) {
		int skor = 0;
		final Monitoring monitoring = monitoringSimulacija.simulirajMerenjePritiska();
		if ((monitoring.getPritisak().getSistolni() > 140) || (monitoring.getPritisak().getDijastolni() > 90)) skor += 1;
		if (pacijent.dobaviGodine() > 60) skor += 1;
		if (tiaRequest.isHemipareza() || tiaRequest.isHemiplegija()) skor += 2;
		if (tiaRequest.isSmetnjeGovora()) skor += 1;
		if (tiaRequest.getTrajanjeSimptoma() >= 60) skor += 2;
		else if (tiaRequest.getTrajanjeSimptoma() > 10) skor += 1;
		if (tiaRequest.isDijabetes()) skor += 1;
		return skor;
	}

	public ProcenaRizikaOdMU izmeniNivoRizika(final UUID idOdluke, final NivoRizikaOdMU noviNivoRizika) {
		final ProcenaRizikaOdMU procenaRizika = procenaRizikaOdMURepository.getReferenceById(idOdluke);
		procenaRizika.setNivoRizika(noviNivoRizika);
		procenaRizikaOdMURepository.save(procenaRizika);
		return procenaRizika;
	}

	public List<Pacijent> dobaviSveAktuelnePacijente() {
		List<ProcenaRizikaOdMU> procene = procenaRizikaOdMURepository.findAllByNivoRizikaEquals(NivoRizikaOdMU.PROCENA_U_TOKU);
		return procene.stream().map(ProcenaRizikaOdMU::getPacijent).collect(Collectors.toList());
	}
}
