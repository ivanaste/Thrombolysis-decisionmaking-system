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

    private List<ZnakIshemije> uoceniZnaciIshemije;

    public List<ZnakIshemije> simulirajOcitavanjeCTa() {
        // Simulirajte očitavanje temperature
        final Random random = new Random();
        final int randomValue = random.nextInt(3);
        return dobaviRandomZnakoveIshemije(randomValue);
    }

    private List<ZnakIshemije> dobaviRandomZnakoveIshemije(final int brojZnakova) {
        final Random random = new Random();
        final ZnakIshemije[] znaci = ZnakIshemije.values();

        final List<ZnakIshemije> randomZnaci = new ArrayList<>();

        for (int i = 0; i < brojZnakova; i++) {
            final int randomIndex = random.nextInt(znaci.length);
            final ZnakIshemije randomZnak = znaci[randomIndex];
            if (!randomZnaci.contains(randomZnak)) {
                randomZnaci.add(znaci[randomIndex]);
            }
        }

        return randomZnaci;
    }
}
