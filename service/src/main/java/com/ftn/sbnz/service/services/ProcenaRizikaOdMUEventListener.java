package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.events.OdlukaOTromboliziEvent;
import com.ftn.sbnz.model.events.ProcenaRizikaOdMUEvent;
import com.ftn.sbnz.model.models.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
        System.out.println("Ime pravila " + event.getMatch().getRule().getName());
        if (matchedObject instanceof ProcenaRizikaOdMUEvent) {
            final ProcenaRizikaOdMUEvent procena = (ProcenaRizikaOdMUEvent) matchedObject;
            if (procena.getNivoRizika().equals(NivoRizikaOdMU.PROCENA_U_TOKU)) {
                System.out.println("Procena u toku");
                //nastavi dalje procenjivanje, prosla prva i druga faza

            } else {
                final ProcenaRizikaOdMU izmenjenaProcena = procenaRizikaOdMUService.izmeniNivoRizika(procena.getIdProceneRizika(), procena.getNivoRizika());
                //sada je nivo visok ili nizak
                PacijentiNaEKGu.remove(izmenjenaProcena.getPacijent().getJmbg());
            }
        }
    }
}
