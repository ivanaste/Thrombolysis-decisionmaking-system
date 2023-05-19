package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.dto.request.NIHHSRequest;
import com.ftn.sbnz.model.dto.request.NastanakSimptomaRequest;
import com.ftn.sbnz.model.dto.request.NeuroloskiPregledRequest;
import com.ftn.sbnz.model.events.OdlukaOTromboliziEvent;
import com.ftn.sbnz.model.models.Laboratorija;
import com.ftn.sbnz.model.models.Monitoring;
import com.ftn.sbnz.model.models.NIHHS;
import com.ftn.sbnz.model.models.Odluka;
import com.ftn.sbnz.model.models.Pritisak;
import com.ftn.sbnz.model.models.Simptomi;
import com.ftn.sbnz.model.models.StatusOdluke;
import com.ftn.sbnz.model.models.ZnakIshemije;
import com.ftn.sbnz.service.repository.OdlukaRepository;
import com.ftn.sbnz.service.simulation.CTSimulacija;
import com.ftn.sbnz.service.simulation.LaboratorijaSimulacija;
import com.ftn.sbnz.service.simulation.MonitoringSimulacija;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OdlukaService {
	private final KieSession kieSession;

	private final OdlukaRepository odlukaRepository;

	private final KorisnikService korisnikService;

	private final CTSimulacija ctSimulacija = new CTSimulacija();

	private final LaboratorijaSimulacija laboratorijaSimulacija = new LaboratorijaSimulacija();

	private final MonitoringSimulacija monitoringSimulacija = new MonitoringSimulacija();

	@Autowired
	public OdlukaService(final KieSession kieSession, final OdlukaRepository odlukaRepository, final KorisnikService korisnikService) {
		this.kieSession = kieSession;
		this.odlukaRepository = odlukaRepository;
		this.korisnikService = korisnikService;
	}

	public String proveriOdlukuNaOsnovuNastankaSimptoma(final NastanakSimptomaRequest nastanakSimptomaRequest) {
		final Simptomi simptomi = new Simptomi(nastanakSimptomaRequest.getTrenutakNastanka(), nastanakSimptomaRequest.getStanjeSvesti(),
			nastanakSimptomaRequest.isNastaliUTokuSna());
		final Odluka odluka = new Odluka(korisnikService.getPacijentByJmbg(nastanakSimptomaRequest.getJmbgPacijenta()), StatusOdluke.PRIHVACENA_FAZA_1);
		odlukaRepository.save(odluka);

		final OdlukaOTromboliziEvent odlukaEvent = new OdlukaOTromboliziEvent(odluka.getId(), StatusOdluke.PRIHVACENA_FAZA_1, simptomi, nastanakSimptomaRequest.isPostojeSvedoci());

		kieSession.addEventListener(new OdlukaOTromboliziEventListener(this));
		kieSession.insert(odlukaEvent);
		kieSession.fireAllRules();
		//kieSession.dispose();
		//return odlukaRepository.getReferenceById(neuroloskiPregled.getIdOdluke()).getStatus().getOpis();
		//moze ovo
		return odlukaRepository.getFirstByPacijent_JmbgOrderByCreatedAtDesc(nastanakSimptomaRequest.getJmbgPacijenta()).getStatus().getOpis();
	}

	public String proveriOdlukuNaOsnovuNeuroloskogPregleda(final NeuroloskiPregledRequest neuroloskiPregled) {
		final OdlukaOTromboliziEvent odlukaEvent = new OdlukaOTromboliziEvent(neuroloskiPregled.getIdOdluke(),
				StatusOdluke.PRIHVACENA_FAZA_2,
				neuroloskiPregled.kreirajKontraindikacije());
		System.out.println(odlukaEvent.getKontraindikacije().get(0).getVrsta());
		System.out.println(odlukaEvent.getKontraindikacije().get(0).getDatum());

		kieSession.addEventListener(new OdlukaOTromboliziEventListener(this));
		kieSession.insert(odlukaEvent);
		kieSession.fireAllRules();

		return odlukaRepository.getReferenceById(neuroloskiPregled.getIdOdluke()).getStatus().getOpis();
	}

	public String proveriOdlukuNaOsnovuNIHHSSkora(final NIHHSRequest nihhsRequest) {
		//final KieSession kieSession = this.kieContainer.newKieSession();
		final Integer skor = izracunajNIHHSSkor(nihhsRequest);
		final NIHHS nihhs = new NIHHS(skor, nihhsRequest.getJmbgPacijenta(), nihhsRequest.getIdOdluke());

		kieSession.addEventListener(new OdlukaOTromboliziEventListener(this));
		kieSession.insert(nihhs);

		kieSession.fireAllRules();
		//kieSession.dispose();
		return odlukaRepository.getReferenceById(nihhsRequest.getIdOdluke()).getStatus().getOpis();
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

	public Odluka izmeniOdlukuZaZadatogPacijenta(final UUID idOdluke, final StatusOdluke noviStatusOdluke) {
		//nije dobro ime metode
		final Odluka odluka = odlukaRepository.getReferenceById(idOdluke);
		odluka.setStatus(noviStatusOdluke);
		odlukaRepository.save(odluka);
		return odluka;
	}

	public void simuliraj(final Odluka izmenjenaOdluka) {
		//simulirajCT
		if (izmenjenaOdluka.getStatus().equals(StatusOdluke.PRIHVACENA_FAZA_4)) {
			//final List<ZnakIshemije> znaci = ctSimulacija.simulirajOcitavanjeCTa();
			final List<ZnakIshemije> znaci = List.of(ZnakIshemije.GUBITAK_GRANICE_IZMEDJU_BELE_I_SIVE_MASE);
			System.out.println("Znaci " + znaci);
			final OdlukaOTromboliziEvent odlukaEvent = new OdlukaOTromboliziEvent(izmenjenaOdluka.getId(), izmenjenaOdluka.getPacijent().getJmbg(),
				izmenjenaOdluka.getStatus(), znaci);

			kieSession.insert(odlukaEvent);
			kieSession.fireAllRules();
		}
	}

	public void simulirajLaboratoriju(final Odluka odluka) {
		final Laboratorija laboratorija = laboratorijaSimulacija.simulirajLaboratoriju();
		laboratorija.setTrombociti(110000.00);
		laboratorija.setGlikemija(20.00);
		laboratorija.setINR(1.65);

		final OdlukaOTromboliziEvent odlukaEvent = new OdlukaOTromboliziEvent(odluka.getId(),
				StatusOdluke.PRIHVACENA_FAZA_6, laboratorija);


		System.out.println("Laboratorija: " + laboratorija.toString());
		System.out.println();
		System.out.println();

		kieSession.insert(odlukaEvent);
		kieSession.fireAllRules();

	}

	public void simulirajMonitoring(final Odluka odluka) {
		final Monitoring monitoring = monitoringSimulacija.simulirajMerenjePritiska();
		//monitoring.getPritisak().setDijastolni(105);
		monitoring.setPritisak(new Pritisak(200, 100));
		System.out.println("Sistolni" + monitoring.getPritisak().getSistolni());

		final OdlukaOTromboliziEvent odlukaEvent = new OdlukaOTromboliziEvent(odluka.getId(),
				StatusOdluke.PRIHVACENA_FAZA_3, monitoring);
		kieSession.insert(odlukaEvent);
		kieSession.fireAllRules();
	}
}
