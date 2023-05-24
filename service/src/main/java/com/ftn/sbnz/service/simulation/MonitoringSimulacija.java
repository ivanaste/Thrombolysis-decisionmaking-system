package com.ftn.sbnz.service.simulation;

import com.ftn.sbnz.model.events.OtkucajSrcaEvent;
import com.ftn.sbnz.model.models.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Component
@Data
@RequiredArgsConstructor
public class MonitoringSimulacija {

    private final Random random = new Random();
    private final KieSession kieSession;
    private final Map<String, Pacijent> pacijentiNaEKGu;

    private boolean prvaSimulacija = true;

    private LocalDateTime lastMinuteCheckTime;

    public Pritisak simulirajMerenjePritiska() {
        Pritisak pritisak = new Pritisak();
        pritisak.setSistolni((int) GenerisiRandomVrednosti.execute(90.00, 180.00));
        pritisak.setDijastolni((int) GenerisiRandomVrednosti.execute(60.00, 120.00));
        return pritisak;
    }

    @Scheduled(fixedDelay = 100)
    public void simulirajOtkucajeSrca() throws InterruptedException {
        if (prvaSimulacija) lastMinuteCheckTime = LocalDateTime.now();
        prvaSimulacija = false;
        Duration duration = Duration.between(lastMinuteCheckTime, LocalDateTime.now());
        if (duration.toMinutes() == 1) {
            this.kieSession.insert("First Minute Passed");
            this.kieSession.fireAllRules();
        }
        //int randomDelay = random.nextInt(100) + 100;

        for(String jmbgPacijenta: pacijentiNaEKGu.keySet()) {
            System.out.println("Otkucaj srca pacijenta ciji je jmbg " + jmbgPacijenta);
            this.kieSession.insert(new OtkucajSrcaEvent(jmbgPacijenta));
            this.kieSession.fireAllRules();
        }
        //Thread.sleep(randomDelay);
    }

    @Scheduled(fixedDelay = 400)
    public void simulirajRRintervale() throws InterruptedException {
        //600 i 1200ms kod zdravog coveka
        int randomDelay = random.nextInt(4000) + 100;

        for(String jmbgPacijenta: pacijentiNaEKGu.keySet()) {
            System.out.println("RR signal " + randomDelay + " pacijenta ciji je jmbg " + jmbgPacijenta);
            this.kieSession.insert(new OtkucajSrcaEvent(jmbgPacijenta, randomDelay));
            this.kieSession.fireAllRules();
        }
        Thread.sleep(randomDelay);
    }
}
