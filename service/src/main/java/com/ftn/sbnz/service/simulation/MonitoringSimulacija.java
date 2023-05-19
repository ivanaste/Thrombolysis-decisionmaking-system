package com.ftn.sbnz.service.simulation;

import com.ftn.sbnz.model.events.OtkucajSrcaEvent;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.service.repository.ProcenaRizikaOdMURepository;
import com.ftn.sbnz.service.services.ProcenaRizikaOdMUService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Data
@NoArgsConstructor(force = true)
public class MonitoringSimulacija {

    private Monitoring monitoring = new Monitoring();
    private final Random random = new Random();
    private final KieSession kieSession;
    private final ProcenaRizikaOdMUService procenaRizikaOdMUService;

    @Autowired
    public MonitoringSimulacija(KieSession kieSession, ProcenaRizikaOdMUService procenaRizikaOdMUService) {
        this.kieSession = kieSession;
        this.procenaRizikaOdMUService = procenaRizikaOdMUService;
    }

    public Monitoring simulirajMerenjePritiska() {
        Pritisak pritisak = new Pritisak();
        pritisak.setSistolni((int) GenerisiRandomVrednosti.execute(90.00, 180.00));
        pritisak.setDijastolni((int) GenerisiRandomVrednosti.execute(60.00, 120.00));
        this.monitoring.setPritisak(pritisak);
        return this.monitoring;
    }

    @Scheduled(fixedDelay = 1000)
    public void simulirajOtkucajeSrca() {
        List<Pacijent> pacijenti = procenaRizikaOdMUService.dobaviSveAktuelnePacijente();
        int randomDelay = random.nextInt(5000) + 1000; // Random delay between 1 and 6 seconds

        // Code to send heartbeat
        for(Pacijent pacijent: pacijenti) {
            System.out.println("Heartbeat signal sent za pacijenta " + pacijent.getJmbg());

            RRInterval rr = new RRInterval();

            // Introduce randomness to the scheduling
            this.kieSession.insert(new OtkucajSrcaEvent(pacijent.getJmbg()));
            this.kieSession.fireAllRules();
        }
        try {
            Thread.sleep(randomDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedDelay = 400)
    public void simulirajRRintervale() {
        //rr intervali se krecu izmedju 600 i 1200ms, kod zdravog coveka (50 do 100 otkucaja srca)
        //400, 1400ms
        List<Pacijent> pacijenti = procenaRizikaOdMUService.dobaviSveAktuelnePacijente();
        int randomDelay = random.nextInt(900) + 100; // Random delay between 100 and 1000 miliseconds

        // Code to send heartbeat
        for(Pacijent pacijent: pacijenti) {
            System.out.println("RR signal za " + pacijent.getJmbg());

            RRInterval rr = new RRInterval();

            // Introduce randomness to the scheduling
            this.kieSession.insert(new OtkucajSrcaEvent(pacijent.getJmbg(), rr));
            this.kieSession.fireAllRules();
        }
        try {
            Thread.sleep(randomDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
