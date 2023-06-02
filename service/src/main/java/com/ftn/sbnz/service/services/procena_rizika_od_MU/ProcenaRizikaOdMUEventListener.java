package com.ftn.sbnz.service.services.procena_rizika_od_MU;

import com.ftn.sbnz.model.events.ProcenaRizikaOdMUEvent;
import com.ftn.sbnz.model.models.*;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProcenaRizikaOdMUEventListener  extends DefaultAgendaEventListener {

    private final ProcenaRizikaOdMUService procenaRizikaOdMUService;
    private final Map<String, Pacijent> PacijentiNaEKGu;

    @Autowired
    public ProcenaRizikaOdMUEventListener(final ProcenaRizikaOdMUService procenaRizika, Map<String, Pacijent> pacijentiNaEKGu) {
        this.procenaRizikaOdMUService = procenaRizika;
        this.PacijentiNaEKGu = pacijentiNaEKGu;
    }

    @Override
    public void afterMatchFired(final AfterMatchFiredEvent event) {
        final Object matchedObject = event.getMatch().getObjects().get(0);
        if (matchedObject instanceof ProcenaRizikaOdMUEvent) {
            final ProcenaRizikaOdMUEvent procena = (ProcenaRizikaOdMUEvent) matchedObject;
            final ProcenaRizikaOdMU izmenjenaProcena = procenaRizikaOdMUService.izmeniNivoRizika(procena.getIdProceneRizika(), procena.getNivoRizika());
            //nivo visok ili nizak
            //PacijentiNaEKGu.remove(izmenjenaProcena.getPacijent().getJmbg());
        }
    }
}
