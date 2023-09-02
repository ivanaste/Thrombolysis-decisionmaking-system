package com.ftn.sbnz.service.simulation;

import com.ftn.sbnz.model.events.OtkucajSrcaEvent;
import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.model.models.Pritisak;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Component
@Data
@RequiredArgsConstructor
public class MonitoringSimulacija {

    private final Random random = new Random();
    private final KieSession kieSession;
    private final Map<String, Pacijent> pacijentiNaEKGu;
    private final Map<String, LocalDateTime> rZubciPacijenata = new HashMap<>();

    private boolean prvaSimulacija = true;

    private LocalDateTime lastMinuteCheckTime;

    public Pritisak simulirajMerenjePritiska() {
        Pritisak pritisak = new Pritisak();
        pritisak.setSistolni((int) GenerisiRandomVrednosti.execute(90.00, 180.00));
        pritisak.setDijastolni((int) GenerisiRandomVrednosti.execute(60.00, 120.00));
        return pritisak;
    }

    @Scheduled(fixedDelay = 100)
    public void simulajRIntervale() throws InterruptedException {
        if (prvaSimulacija) lastMinuteCheckTime = LocalDateTime.now();
        prvaSimulacija = false;
        Duration duration = Duration.between(lastMinuteCheckTime, LocalDateTime.now());
        if (duration.toSeconds() == 10) {
            this.kieSession.insert("First Minute Passed");
            this.kieSession.fireAllRules();
        }

        //600 i 1200ms kod zdravog coveka
        int randomDelay = random.nextInt(1200);
        int brojPZubaca = random.nextInt(3) + random.nextInt(3);
        UUID rZubacId = UUID.randomUUID();

        for (String jmbgPacijenta : pacijentiNaEKGu.keySet()) {
            LocalDateTime vremeRZubca = LocalDateTime.now();
            if (rZubciPacijenata.containsKey(jmbgPacijenta)) {
                long rrInterval = Duration.between(vremeRZubca, rZubciPacijenata.get(jmbgPacijenta)).toMillis();
                this.kieSession.insert(new OtkucajSrcaEvent(jmbgPacijenta, (int) rrInterval));
            }
            for (int i = 0; i < brojPZubaca; i++) {
                System.out.println("P zubac  pacijenta ciji je jmbg " + jmbgPacijenta);
                this.kieSession.insert(new OtkucajSrcaEvent(jmbgPacijenta, rZubacId, UUID.randomUUID()));
            }
            this.kieSession.insert(new OtkucajSrcaEvent(jmbgPacijenta, rZubacId));
            System.out.println("R zubac " + vremeRZubca + " pacijenta ciji je jmbg " + jmbgPacijenta);
            rZubciPacijenata.put(jmbgPacijenta, vremeRZubca);
            this.kieSession.fireAllRules();
        }
        Thread.sleep(randomDelay);
    }
}
