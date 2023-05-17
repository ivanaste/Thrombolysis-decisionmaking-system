package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.events.OdlukaOTromboliziEvent;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OdlukaOTromboliziEventListener extends DefaultAgendaEventListener {

	private final OdlukaService odlukaService;

	@Autowired
	public OdlukaOTromboliziEventListener(final OdlukaService odlukaService) {
		this.odlukaService = odlukaService;
	}

	@Override
	public void afterMatchFired(final AfterMatchFiredEvent event) {
		final Object matchedObject = event.getMatch().getObjects().get(0);
		System.out.println(matchedObject);
		if (matchedObject instanceof OdlukaOTromboliziEvent) {
			final OdlukaOTromboliziEvent ruleEvent = (OdlukaOTromboliziEvent) matchedObject;

			odlukaService.izmeniOdlukuZaZadatogPacijenta(ruleEvent.getJmbgPacijenta(), ruleEvent.getStatusOdluke());
		}
	}
}
