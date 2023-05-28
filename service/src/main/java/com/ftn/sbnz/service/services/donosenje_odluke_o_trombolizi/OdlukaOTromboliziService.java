package com.ftn.sbnz.service.services.donosenje_odluke_o_trombolizi;

import com.ftn.sbnz.model.dto.request.NIHHSRequest;
import com.ftn.sbnz.model.dto.request.NastanakSimptomaRequest;
import com.ftn.sbnz.model.dto.request.NeuroloskiPregledRequest;
import com.ftn.sbnz.model.events.OdlukaOTromboliziEvent;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.service.repository.OdlukaOTromboliziRepository;
import com.ftn.sbnz.service.repository.PersonRepository;
import com.ftn.sbnz.service.services.alarm.AlarmListener;
import com.ftn.sbnz.service.services.korisnik.KorisnikService;
import com.ftn.sbnz.service.services.mail.SendMail;
import com.ftn.sbnz.service.simulation.CTSimulacija;
import com.ftn.sbnz.service.simulation.LaboratorijaSimulacija;
import com.ftn.sbnz.service.simulation.MonitoringSimulacija;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class OdlukaOTromboliziService {

    @Autowired
    public OdlukaOTromboliziService(final KieSession kieSession, final PersonRepository personRepository, final SendMail sendMail, final OdlukaOTromboliziRepository odlukaOTromboliziRepository, final KorisnikService korisnikService, final MonitoringSimulacija monitoringSimulacija) {
        this.kieSession = kieSession;
        this.odlukaOTromboliziRepository = odlukaOTromboliziRepository;
        this.korisnikService = korisnikService;
        this.monitoringSimulacija = monitoringSimulacija;
        this.laboratorijaSimulacija = new LaboratorijaSimulacija();
        this.ctSimulacija = new CTSimulacija();
        this.sendMail = sendMail;
        this.personRepository = personRepository;
        OdlukaOTromboliziEventListener eventListener = new OdlukaOTromboliziEventListener(this);
        AlarmListener alarmListener = new AlarmListener(this.sendMail, this.personRepository);
        kieSession.addEventListener(eventListener);
        kieSession.addEventListener(alarmListener);
    }

    private final KieSession kieSession;

    private final OdlukaOTromboliziRepository odlukaOTromboliziRepository;

    private final KorisnikService korisnikService;

    private final CTSimulacija ctSimulacija;

    private final LaboratorijaSimulacija laboratorijaSimulacija;

    private final MonitoringSimulacija monitoringSimulacija;
    private final SendMail sendMail;
    private final PersonRepository personRepository;


    public Odluka proveriOdlukuNaOsnovuNastankaSimptoma(final NastanakSimptomaRequest nastanakSimptomaRequest) {
        Pacijent pacijent = korisnikService.sacuvajPacijenta(nastanakSimptomaRequest.getJmbgPacijenta(), nastanakSimptomaRequest.getDatumRodjenjaPacijenta());
        final Simptomi simptomi = new Simptomi(nastanakSimptomaRequest.getTrenutakNastanka(), nastanakSimptomaRequest.getStanjeSvesti(),
                nastanakSimptomaRequest.isNastaliUTokuSna());
        final Odluka odluka = new Odluka(pacijent, StatusOdluke.PRIHVACENA_FAZA_1);
        odlukaOTromboliziRepository.save(odluka);
        final OdlukaOTromboliziEvent odlukaEvent = new OdlukaOTromboliziEvent(odluka.getId(), StatusOdluke.PRIHVACENA_FAZA_1, simptomi, nastanakSimptomaRequest.isPostojeSvedoci());
        kieSession.insert(odlukaEvent);
        kieSession.fireAllRules();

        return odluka;
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
        final NIHHS nihhs = new NIHHS(skor, nihhsRequest.getJmbgPacijenta(), nihhsRequest.getIdOdluke());
        kieSession.insert(nihhs);
        kieSession.fireAllRules();
        return odlukaOTromboliziRepository.getReferenceById(nihhsRequest.getIdOdluke());
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
        pritisak.setDijastolni(100);
        pritisak.setSistolni(120);

        final OdlukaOTromboliziEvent odlukaEvent = new OdlukaOTromboliziEvent(odluka.getId(),
                StatusOdluke.PRIHVACENA_FAZA_3, pritisak);
        kieSession.insert(odlukaEvent);
        kieSession.fireAllRules();
    }

    public Odluka izmeniStatusOdluke(final UUID idOdluke, final StatusOdluke noviStatusOdluke) {
        final Odluka odluka = odlukaOTromboliziRepository.findOdlukaById(idOdluke);
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
        List<Odluka> odluke = odlukaOTromboliziRepository.findAll();
        Collections.reverse(odluke);
        return odluke;
    }
}
