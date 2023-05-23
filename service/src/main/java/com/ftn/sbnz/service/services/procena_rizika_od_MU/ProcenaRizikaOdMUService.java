package com.ftn.sbnz.service.services.procena_rizika_od_MU;

import com.ftn.sbnz.model.dto.request.ProcenaRizikaOdMURequest;
import com.ftn.sbnz.model.events.ProcenaRizikaOdMUEvent;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.service.repository.ProcenaRizikaOdMURepository;
import com.ftn.sbnz.service.services.korisnik.KorisnikService;
import com.ftn.sbnz.service.simulation.MonitoringSimulacija;

import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProcenaRizikaOdMUService {

	private final KorisnikService korisnikService;
	private final KieSession kieSession;
	private final ProcenaRizikaOdMURepository procenaRizikaOdMURepository;
	private final Map<String, Pacijent> PacijentiNaEKGu;
	private final MonitoringSimulacija monitoringSimulacija;

	public String utvrdiNivoRizikaOdMU(final ProcenaRizikaOdMURequest procenaRizikaOdMURequest) {
		final Pacijent pacijent = korisnikService.getPacijentByJmbg(procenaRizikaOdMURequest.getJmbgPacijenta());
		final Integer ABCD2Skor = izracunajABCD2Skor(procenaRizikaOdMURequest, pacijent);

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

	public String utvrdiNivoRizikaTemplejt(final ProcenaRizikaOdMURequest procenaRizikaOdMURequest) {
		final Pacijent pacijent = korisnikService.getPacijentByJmbg(procenaRizikaOdMURequest.getJmbgPacijenta());
		final Integer ABCD2Skor = izracunajABCD2Skor(procenaRizikaOdMURequest, pacijent);

		System.out.println("ABC" + ABCD2Skor);
		ProcenaRizikaOdMU procenaRizika = new ProcenaRizikaOdMU(pacijent, NivoRizikaOdMU.PROCENA_U_TOKU);
		procenaRizika = procenaRizikaOdMURepository.save(procenaRizika);

		InputStream template = ProcenaRizikaOdMUService.class.getResourceAsStream("/rules/template/template.drt");

		List<TemplateModel> data = new ArrayList<>();
		data.add(new TemplateModel(0, 4, 1, NivoRizikaOdMU.PROCENA_U_TOKU, NivoRizikaOdMU.NIZAK_RIZIK));
		data.add(new TemplateModel(4, 6, 50, NivoRizikaOdMU.PROCENA_U_TOKU, NivoRizikaOdMU.NIZAK_RIZIK));
		data.add(new TemplateModel(6, 8, 1, NivoRizikaOdMU.PROCENA_U_TOKU, NivoRizikaOdMU.VISOK_RIZIK));

		ObjectDataCompiler converter = new ObjectDataCompiler();
		String drl = converter.compile(data, template);

		System.out.println(drl);

		KieSession kSession = createKieSessionFromDRL(drl);
		final ProcenaRizikaOdMUEvent procenaRizikaEvent = new ProcenaRizikaOdMUEvent(procenaRizika.getId(), pacijent.getJmbg(), NivoRizikaOdMU.PROCENA_U_TOKU, ABCD2Skor, procenaRizikaOdMURequest.getStenozaSimptomatskogKrvnogSuda());
		if (kSession.getAgendaEventListeners().size() == 0) kSession.addEventListener(new ProcenaRizikaOdMUEventListener(this, PacijentiNaEKGu));
		kSession.insert(procenaRizika.getId());
		kSession.insert(procenaRizikaEvent);
		kSession.fireAllRules();

		ProcenaRizikaOdMU izmenjenaProcena = procenaRizikaOdMURepository.getReferenceById(procenaRizika.getId());
		NivoRizikaOdMU nivoRizika = izmenjenaProcena.getNivoRizika();
		if (nivoRizika.equals(NivoRizikaOdMU.PROCENA_U_TOKU)) {
			kSession.insert(new ProcenaRizikaOdMUEvent(izmenjenaProcena.getId(), izmenjenaProcena.getPacijent().getJmbg(), NivoRizikaOdMU.PROCENA_RIZIKA_OD_ATRIJALNE_FIBRILACIJE));
			kSession.fireAllRules();
		}

		nivoRizika = dobaviKonacniNivoRizika(izmenjenaProcena);
		PacijentiNaEKGu.remove(pacijent.getJmbg());

		//brisanje zbog pravila: TIA unutar tri dana
		FactHandle factHandle = kSession.getFactHandle(procenaRizika.getId());
		kSession.delete(factHandle);

		return nivoRizika.name();
	}

	private KieSession createKieSessionFromDRL(String drl){
		KieHelper kieHelper = new KieHelper();
		kieHelper.addContent(drl, ResourceType.DRL);

		Results results = kieHelper.verify();

		if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
			List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
			for (Message message : messages) {
				System.out.println("Error: "+message.getText());
			}

			throw new IllegalStateException("Compilation errors were found. Check the logs.");
		}

		return kieHelper.build().newKieSession();
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
