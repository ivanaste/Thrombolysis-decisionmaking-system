package com.ftn.sbnz.service.services;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.dto.request.NIHHSRequest;
import com.ftn.sbnz.model.events.OdlukaOTromboliziEvent;
import com.ftn.sbnz.model.models.NIHHS;
import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.model.models.Simptomi;
import com.ftn.sbnz.model.models.Odluka;
import com.ftn.sbnz.model.dto.request.NastanakSimptomaRequest;
import com.ftn.sbnz.model.models.StatusOdluke;
import com.ftn.sbnz.service.repository.OdlukaRepository;
import com.ftn.sbnz.service.repository.SimptomiRepository;

import java.util.Arrays;

@Service
public class OdlukaService {

	private static Logger log = LoggerFactory.getLogger(OdlukaService.class);

	private final KieContainer kieContainer;

	private final OdlukaRepository odlukaRepository;

	private final KorisnikService korisnikService;
	private final SimptomiRepository simptomiRepository;

	@Autowired
	public OdlukaService(KieContainer kieContainer, OdlukaRepository odlukaRepository, KorisnikService korisnikService,
		final SimptomiRepository simptomiRepository) {
		log.info("Initialising a new example session.");
		this.kieContainer = kieContainer;
		this.odlukaRepository = odlukaRepository;
		this.korisnikService = korisnikService;
		this.simptomiRepository = simptomiRepository;
	}

	public String proveriOdlukuNaOsnovuNastankaSimptoma(NastanakSimptomaRequest nastanakSimptomaRequest) {
		KieSession kieSession = this.kieContainer.newKieSession();
		Pacijent pacijent = korisnikService.getPacijentByJmbg(nastanakSimptomaRequest.getJmbgPacijenta());

		Simptomi simptomi = new Simptomi(nastanakSimptomaRequest.getTrenutakNastanka(), nastanakSimptomaRequest.getStanjeSvesti(), nastanakSimptomaRequest.isNastaliUTokuSna());
		Odluka odluka = Odluka.builder().pacijent(pacijent).status(StatusOdluke.PRIHVACENA_FAZA_1).simptomi(simptomi).postojeSvedoci(nastanakSimptomaRequest.isPostojeSvedoci()).build();
		kieSession.insert(odluka);
		kieSession.fireAllRules();
		kieSession.dispose();

		odlukaRepository.save(odluka);
		return odluka.getStatus().getOpis();
	}

	public String proveriOdlukuNaOsnovuNIHHSSkora(NIHHSRequest nihhsRequest) {
		KieSession kieSession = this.kieContainer.newKieSession();
		Integer skor = izracunajNIHHSSkor(nihhsRequest);
		NIHHS nihhs = new NIHHS(skor, nihhsRequest.getJmbgPacijenta());
		// ovo ti izgleda treba
		kieSession.addEventListener(new OdlukaOTromboliziEventListener(this));
		kieSession.insert(nihhs);

		kieSession.fireAllRules();
		kieSession.dispose();
		return odlukaRepository.getFirstByPacijent_JmbgOrderByCreatedAtDesc(nihhsRequest.getJmbgPacijenta()).getStatus().getOpis();
	}

	public Integer izracunajNIHHSSkor(NIHHSRequest nihhsRequest) {
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

	public Odluka izmeniOdlukuZaZadatogPacijenta(String jmbgPacijenta, StatusOdluke noviStatusOdluke) {
		Pacijent pacijent = korisnikService.getPacijentByJmbg(jmbgPacijenta);
		Odluka odlukaOPrimeni = odlukaRepository.getOdlukaByPacijentAndStatusNotIn(pacijent, Arrays.asList(StatusOdluke.PRIHVACENA, StatusOdluke.ODBIJENA));
		odlukaOPrimeni.setStatus(noviStatusOdluke);
		odlukaRepository.save(odlukaOPrimeni);
		return odlukaOPrimeni;
	}
}
