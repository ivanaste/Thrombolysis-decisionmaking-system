package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.events.OdlukaOTromboliziEvent;
import com.ftn.sbnz.model.models.Odluka;
import com.ftn.sbnz.model.models.StatusOdluke;
import com.ftn.sbnz.service.simulation.CTSimulacija;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OdlukaOTromboliziEventListener extends DefaultAgendaEventListener {

	private final OdlukaService odlukaService;

	private final CTSimulacija ctSimulacija = new CTSimulacija();

	@Autowired
	public OdlukaOTromboliziEventListener(final OdlukaService odlukaService) {
		this.odlukaService = odlukaService;
	}

	@Override
	public void afterMatchFired(final AfterMatchFiredEvent event) {
		final Object matchedObject = event.getMatch().getObjects().get(0);
		System.out.println(matchedObject);
		System.out.println("Ime pravila " + event.getMatch().getRule().getName());
		if (matchedObject instanceof OdlukaOTromboliziEvent) {
			final OdlukaOTromboliziEvent ruleEvent = (OdlukaOTromboliziEvent) matchedObject;
			final Odluka izmenjenaOdluka = odlukaService.izmeniOdlukuZaZadatogPacijenta(ruleEvent.getIdOdluke(), ruleEvent.getStatusOdluke());

			if (!event.getMatch().getRule().getName().contains("Faza 5")) {
				odlukaService.simuliraj(izmenjenaOdluka);
			}

			if (izmenjenaOdluka.getStatus().equals(StatusOdluke.PRIHVACENA_FAZA_5)) {
				odlukaService.simulirajLaboratoriju(izmenjenaOdluka);
			}

			if (izmenjenaOdluka.getStatus().equals(StatusOdluke.PRIHVACENA_FAZA_2)) {
				odlukaService.simulirajMonitoring(izmenjenaOdluka);
			}
		}
	}
}
