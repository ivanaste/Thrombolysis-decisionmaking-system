package com.ftn.sbnz.service.simulation;

import com.ftn.sbnz.model.models.Laboratorija;
import com.ftn.sbnz.model.models.Monitoring;
import com.ftn.sbnz.model.models.Pritisak;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Data
public class MonitoringSimulacija {

    private Monitoring monitoring = new Monitoring();

    public Monitoring simulirajMerenjePritiska() {
        Pritisak pritisak = new Pritisak();
        pritisak.setSistolni((int) GenerisiRandomVrednosti.execute(90.00, 180.00));
        pritisak.setDijastolni((int) GenerisiRandomVrednosti.execute(60.00, 120.00));
        this.monitoring.setPritisak(pritisak);
        return this.monitoring;
    }
}
