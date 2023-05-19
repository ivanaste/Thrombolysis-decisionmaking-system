package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.events.OdlukaOTromboliziEvent;
import com.ftn.sbnz.model.events.ProcenaRizikaOdMUEvent;
import com.ftn.sbnz.model.models.NivoRizikaOdMU;
import com.ftn.sbnz.model.models.Odluka;
import com.ftn.sbnz.model.models.ProcenaRizikaOdMU;
import com.ftn.sbnz.model.models.StatusOdluke;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcenaRizikaOdMUEventListener  extends DefaultAgendaEventListener {
    private final ProcenaRizikaOdMUService procenaRizikaOdMUService;
    @Autowired
    public ProcenaRizikaOdMUEventListener(final ProcenaRizikaOdMUService procenaRizika) {
        this.procenaRizikaOdMUService = procenaRizika;
    }
    @Override
    public void afterMatchFired(final AfterMatchFiredEvent event) {
        final Object matchedObject = event.getMatch().getObjects().get(0);
        System.out.println("Ime pravila " + event.getMatch().getRule().getName());
        if (matchedObject instanceof ProcenaRizikaOdMUEvent) {
            final ProcenaRizikaOdMUEvent ruleEvent = (ProcenaRizikaOdMUEvent) matchedObject;
            if (ruleEvent.getNivoRizika().equals(NivoRizikaOdMU.PROCENA_U_TOKU)) {
                //nastavi dalje procenjivanje, prosla prva i druga faza

            } else {
                final ProcenaRizikaOdMU izmenjenaProcena = procenaRizikaOdMUService.izmeniNivoRizika(ruleEvent.getIdProceneRizika(), ruleEvent.getNivoRizika());
            }
//            if (!event.getMatch().getRule().getName().contains("Faza 5")) {
//                odlukaService.simuliraj(izmenjenaOdluka);
//            }

//            if (izmenjenaOdluka.getStatus().equals(StatusOdluke.PRIHVACENA_FAZA_5)) {
//                odlukaService.simulirajLaboratoriju(izmenjenaOdluka);
//            }
//
//            if (izmenjenaOdluka.getStatus().equals(StatusOdluke.PRIHVACENA_FAZA_2)) {
//                odlukaService.simulirajMonitoring(izmenjenaOdluka);
//            }
        }
    }
}
