package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.events.OdlukaOTromboliziEvent;
import com.ftn.sbnz.model.models.Odluka;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OdlukaOTromboliziEventListener extends DefaultAgendaEventListener {

	private final OdlukaOTromboliziService odlukaOTromboliziService;

	@Autowired
	public OdlukaOTromboliziEventListener(final OdlukaOTromboliziService odlukaOTromboliziService) {
		this.odlukaOTromboliziService = odlukaOTromboliziService;
	}

	@Override
	public void afterMatchFired(final AfterMatchFiredEvent event) {
		final Object matchedObject = event.getMatch().getObjects().get(0);
		if (matchedObject instanceof OdlukaOTromboliziEvent) {
			final OdlukaOTromboliziEvent ruleEvent = (OdlukaOTromboliziEvent) matchedObject;
			final Odluka izmenjenaOdluka = odlukaOTromboliziService.izmeniOdlukuZaZadatogPacijenta(ruleEvent.getIdOdluke(), ruleEvent.getStatusOdluke());
			switch (izmenjenaOdluka.getStatus()) {
				case PRIHVACENA_FAZA_2:
					odlukaOTromboliziService.simulirajMonitoring(izmenjenaOdluka);
					break;
				case PRIHVACENA_FAZA_4:
					odlukaOTromboliziService.simulirajCT(izmenjenaOdluka);
					break;
				case PRIHVACENA_FAZA_5:
					odlukaOTromboliziService.simulirajLaboratoriju(izmenjenaOdluka);
					break;
			}
//			if (izmenjenaOdluka.getStatus().equals(StatusOdluke.PRIHVACENA_FAZA_2)) {
//				odlukaService.simulirajMonitoring(izmenjenaOdluka);
//			}
//
//			if (izmenjenaOdluka.getStatus().equals(StatusOdluke.PRIHVACENA_FAZA_5)) {
//				odlukaService.simulirajLaboratoriju(izmenjenaOdluka);
//			}
//
//			if (izmenjenaOdluka.getStatus().equals(StatusOdluke.PRIHVACENA_FAZA_4)) {
//				odlukaService.simulirajCT(izmenjenaOdluka);
//			}


//			if (!event.getMatch().getRule().getName().contains("Faza 5")) {
//				odlukaService.simulirajCT(izmenjenaOdluka);
//			}
		}
	}
}
