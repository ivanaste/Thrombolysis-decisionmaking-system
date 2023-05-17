package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.dto.request.NIHHSRequest;
import com.ftn.sbnz.model.dto.request.NastanakSimptomaRequest;
import com.ftn.sbnz.model.events.OdlukaOTromboliziEvent;
import com.ftn.sbnz.model.models.NIHHS;
import com.ftn.sbnz.model.models.Odluka;
import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.model.models.Simptomi;
import com.ftn.sbnz.model.models.StatusOdluke;
import com.ftn.sbnz.model.models.ZnakIshemije;
import com.ftn.sbnz.service.repository.OdlukaRepository;
import com.ftn.sbnz.service.simulation.CTSimulacija;

import java.util.List;
import java.util.UUID;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OdlukaService {

	private static final Logger log = LoggerFactory.getLogger(OdlukaService.class);

	private final KieSession kieSession;

	private final OdlukaRepository odlukaRepository;

	private final KorisnikService korisnikService;

	private final CTSimulacija ctSimulacija = new CTSimulacija();

	@Autowired
	public OdlukaService(final KieSession kieSession, final OdlukaRepository odlukaRepository, final KorisnikService korisnikService) {
		log.info("Initialising a new example session.");
		this.kieSession = kieSession;
		this.odlukaRepository = odlukaRepository;
		this.korisnikService = korisnikService;
	}

	public String proveriOdlukuNaOsnovuNastankaSimptoma(final NastanakSimptomaRequest nastanakSimptomaRequest) {
		final Simptomi simptomi = new Simptomi(nastanakSimptomaRequest.getTrenutakNastanka(), nastanakSimptomaRequest.getStanjeSvesti(),
			nastanakSimptomaRequest.isNastaliUTokuSna());
		final Odluka odluka = new Odluka(korisnikService.getPacijentByJmbg(nastanakSimptomaRequest.getJmbgPacijenta()), StatusOdluke.PRIHVACENA_FAZA_1);
		odlukaRepository.save(odluka);

		final OdlukaOTromboliziEvent odlukaEvent = new OdlukaOTromboliziEvent(odluka.getId(), nastanakSimptomaRequest.getJmbgPacijenta(),
			StatusOdluke.PRIHVACENA_FAZA_1,
			simptomi,
			nastanakSimptomaRequest.isPostojeSvedoci());

		kieSession.addEventListener(new OdlukaOTromboliziEventListener(this));
		kieSession.insert(odlukaEvent);
		kieSession.fireAllRules();
		//kieSession.dispose();
		return odlukaRepository.getFirstByPacijent_JmbgOrderByCreatedAtDesc(nastanakSimptomaRequest.getJmbgPacijenta()).getStatus().getOpis();
	}

	public String proveriOdlukuNaOsnovuNIHHSSkora(final NIHHSRequest nihhsRequest) {
		//final KieSession kieSession = this.kieContainer.newKieSession();
		final Integer skor = izracunajNIHHSSkor(nihhsRequest);
		final NIHHS nihhs = new NIHHS(skor, nihhsRequest.getJmbgPacijenta(), nihhsRequest.getIdOdluke());
		// ovo ti izgleda treba
		System.out.println("u kodu " + nihhs.getIdOdluke());
		kieSession.addEventListener(new OdlukaOTromboliziEventListener(this));
		kieSession.insert(nihhs);

		kieSession.fireAllRules();
		//kieSession.dispose();
		return odlukaRepository.getFirstByPacijent_JmbgOrderByCreatedAtDesc(nihhsRequest.getJmbgPacijenta()).getStatus().getOpis();
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

	public Odluka izmeniOdlukuZaZadatogPacijenta(final UUID idOdluke, final String jmbgPacijenta, final StatusOdluke noviStatusOdluke) {
		final Pacijent pacijent = korisnikService.getPacijentByJmbg(jmbgPacijenta);
		//		final Optional<Odluka> odlukaOptional = odlukaRepository.getReferenceById(idOdluke);
		//		Odluka odluka = null;
		//		if (odlukaOptional.isPresent()) {
		//			odluka = odlukaOptional.get();
		//			odluka.setStatus(noviStatusOdluke);
		//		} else if (noviStatusOdluke.equals(StatusOdluke.PRIHVACENA_FAZA_1) || noviStatusOdluke.equals(StatusOdluke.ODBIJENA)) {
		//			odluka = new Odluka(pacijent, noviStatusOdluke);
		//		}
		//		odlukaRepository.save(odluka);
		final Odluka odluka = odlukaRepository.getReferenceById(idOdluke);
		odluka.setStatus(noviStatusOdluke);
		odlukaRepository.save(odluka);
		return odluka;
	}

	public void simuliraj(final Odluka izmenjenaOdluka) {
		//simulirajCT
		if (izmenjenaOdluka.getStatus().equals(StatusOdluke.PRIHVACENA_FAZA_4)) {
			final List<ZnakIshemije> znaci = ctSimulacija.simulirajOcitavanjeCTa();
			System.out.println("Znaci " + znaci);
			final OdlukaOTromboliziEvent odlukaEvent = new OdlukaOTromboliziEvent(izmenjenaOdluka.getId(), izmenjenaOdluka.getPacijent().getJmbg(),
				izmenjenaOdluka.getStatus(), znaci);
			//Arrays.asList(ZnakIshemije.GUBITAK_GRANICE_IZMEDJU_BELE_I_SIVE_MASE)
			//kieSession.addEventListener(new OdlukaOTromboliziEventListener(this));
			kieSession.insert(odlukaEvent);

			kieSession.fireAllRules();
		}
	}
}
