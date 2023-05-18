package com.ftn.sbnz.service.simulation;

import com.ftn.sbnz.model.models.Laboratorija;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Data
public class LaboratorijaSimulacija {

    private Laboratorija laboratorija = new Laboratorija();

    public Laboratorija simulirajLaboratoriju() {
        this.laboratorija.setGlikemija(GenerisiRandomVrednosti.execute(1.5, 25));
        this.laboratorija.setTrombociti(GenerisiRandomVrednosti.execute(80000, 400000));
        this.laboratorija.setINR(GenerisiRandomVrednosti.execute(1, 3));
        this.laboratorija.setCProtein(GenerisiRandomVrednosti.execute(5, 30));
        this.laboratorija.setSedimentacijaEritrocita(GenerisiRandomVrednosti.execute(15, 25));
        return this.laboratorija;
    }
}
