package com.ftn.sbnz.service.services.procena_rizika_od_MU;

import com.ftn.sbnz.model.dto.request.ProcenaRizikaOdMURequest;
import com.ftn.sbnz.model.events.ProcenaRizikaOdMUEvent;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.service.repository.ProcenaRizikaOdMURepository;
import com.ftn.sbnz.service.services.korisnik.KorisnikService;
import com.ftn.sbnz.service.simulation.MonitoringSimulacija;

import org.drools.core.BeliefSystemType;
import org.drools.core.SessionConfiguration;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcenaRizikaOdMUService {

	private final KorisnikService korisnikService;
	private final KieSession kieSession;
	private final ProcenaRizikaOdMURepository procenaRizikaOdMURepository;
	private final Map<String, Pacijent> PacijentiNaEKGu;
	private final MonitoringSimulacija monitoringSimulacija;

	public String utvrdiNivoRizikaTemplejt(final ProcenaRizikaOdMURequest procenaRizikaOdMURequest) throws IOException {
		final Pacijent pacijent = korisnikService.getPacijentByJmbg(procenaRizikaOdMURequest.getJmbgPacijenta());
		final Integer ABCD2Skor = izracunajABCD2Skor(procenaRizikaOdMURequest, pacijent);

		System.out.println("ABCD2 skor: " + ABCD2Skor);
		ProcenaRizikaOdMU procenaRizika = new ProcenaRizikaOdMU(pacijent, NivoRizikaOdMU.PROCENA_U_TOKU);
		procenaRizika = procenaRizikaOdMURepository.save(procenaRizika);

		//PacijentiNaEKGu.put(procenaRizika.getPacijent().getJmbg(), procenaRizika.getPacijent());

		final ProcenaRizikaOdMUEvent procenaRizikaEvent = new ProcenaRizikaOdMUEvent(procenaRizika.getId(), pacijent.getJmbg(), NivoRizikaOdMU.PROCENA_U_TOKU, ABCD2Skor, procenaRizikaOdMURequest.getStenozaSimptomatskogKrvnogSuda());
		if (kieSession.getAgendaEventListeners().size() == 0) kieSession.addEventListener(new ProcenaRizikaOdMUEventListener(this, PacijentiNaEKGu));
		kieSession.insert(procenaRizika.getId());
		kieSession.insert(procenaRizikaEvent);
		kieSession.fireAllRules();

		ProcenaRizikaOdMU izmenjenaProcena = procenaRizikaOdMURepository.getReferenceById(procenaRizika.getId());
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

		return nivoRizika.name();
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
		if (pacijent.dobaviGodine() > 60) skor += 1;
		if (procenaRizikaOdMURequest.isHemipareza() || procenaRizikaOdMURequest.isHemiplegija()) skor += 2;
		if (procenaRizikaOdMURequest.isSmetnjeGovora()) skor += 1;
		if (procenaRizikaOdMURequest.getTrajanjeSimptoma() >= 60) skor += 2;
		else if (procenaRizikaOdMURequest.getTrajanjeSimptoma() > 10) skor += 1;
		if (procenaRizikaOdMURequest.isDijabetes()) skor += 1;
		return skor;
	}

	public ProcenaRizikaOdMU izmeniNivoRizika(final UUID idOdluke, final NivoRizikaOdMU noviNivoRizika) {
		final ProcenaRizikaOdMU procenaRizika = procenaRizikaOdMURepository.getReferenceById(idOdluke);
		procenaRizika.setNivoRizika(noviNivoRizika);
		procenaRizikaOdMURepository.save(procenaRizika);
		return procenaRizika;
	}

}
