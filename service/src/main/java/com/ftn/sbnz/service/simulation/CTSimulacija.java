package com.ftn.sbnz.service.simulation;

import com.ftn.sbnz.model.models.ZnakIshemije;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Data
public class CTSimulacija {

    private static final Integer MAX_BROJ_ZNAKOVA = 3;

    public List<ZnakIshemije> simulirajOcitavanjeCTa() {
        final Random random = new Random();
        final ZnakIshemije[] moguciZnaci = ZnakIshemije.values();

        final List<ZnakIshemije> uoceniZnaci = new ArrayList<>();

        for (int i = 0; i < MAX_BROJ_ZNAKOVA; i++) {
            final int randomIndex = random.nextInt(moguciZnaci.length);
            final ZnakIshemije randomZnak = moguciZnaci[randomIndex];
            if (!uoceniZnaci.contains(randomZnak)) {
                uoceniZnaci.add(moguciZnaci[randomIndex]);
            }
        }
        return uoceniZnaci;    }
}

