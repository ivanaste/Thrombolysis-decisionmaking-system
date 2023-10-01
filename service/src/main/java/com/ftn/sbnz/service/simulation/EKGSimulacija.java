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
public class EKGSimulacija {

    private final Random random = new Random();
    private final KieSession kieSession;
    private final Map<String, Pacijent> pacijentiNaEKGu;
    private final Map<String, LocalDateTime> rZubciPacijenata = new HashMap<>();

    private boolean prvaSimulacija = true;

    private LocalDateTime pocetnoVreme;

    public Pritisak simulirajMerenjePritiska() {
        Pritisak pritisak = new Pritisak();
        pritisak.setSistolni((int) GenerisiRandomVrednosti.execute(90.00, 200.00));
        pritisak.setDijastolni((int) GenerisiRandomVrednosti.execute(60.00, 120.00));
        return pritisak;
    }

    @Scheduled(fixedDelay = 100)
    public void simulirajEKG() throws InterruptedException {
        zapocniVreme();

        int randomDelay = (int) GenerisiRandomVrednosti.execute(500.00, 1500.00);
        int brojPZubaca = (int) GenerisiRandomVrednosti.execute(1.00, 4.00);
        UUID rZubacId = UUID.randomUUID();

        for (String jmbg : pacijentiNaEKGu.keySet()) {
            LocalDateTime vremeRZubca = LocalDateTime.now();
            if (rZubciPacijenata.containsKey(jmbg)) {
                long rrInterval = Duration.between(vremeRZubca, rZubciPacijenata.get(jmbg)).toMillis();
                this.kieSession.insert(new OtkucajSrcaEvent(jmbg, (int) rrInterval));
            }
            insertPZubce(brojPZubaca, rZubacId, jmbg);
            insertOtkucajSrca(jmbg, rZubacId);
            rZubciPacijenata.put(jmbg, vremeRZubca);

            this.kieSession.fireAllRules();
        }
        Thread.sleep(randomDelay);
    }

    private void insertMinutaProsla() {
        Duration trajanje = Duration.between(pocetnoVreme, LocalDateTime.now());
        if (trajanje.toSeconds() == 60) {
            this.kieSession.insert("First Minute Passed");
            this.kieSession.fireAllRules();
        }
    }

    private void insertOtkucajSrca(String jmbg, UUID rZubacId) {
        this.kieSession.insert(new OtkucajSrcaEvent(jmbg, rZubacId));
    }

    private void insertPZubce(int brojPZubaca, UUID rZubacId, String jmbg) {
        for (int i = 0; i < brojPZubaca; i++) {
            System.out.println("P zubac  pacijenta ciji je jmbg " + jmbg);
            this.kieSession.insert(new OtkucajSrcaEvent(jmbg, rZubacId, UUID.randomUUID()));
        }
    }

    private void zapocniVreme() {
        if (prvaSimulacija) {
            pocetnoVreme = LocalDateTime.now();
            prvaSimulacija = false;
        }
        insertMinutaProsla();
    }
}
