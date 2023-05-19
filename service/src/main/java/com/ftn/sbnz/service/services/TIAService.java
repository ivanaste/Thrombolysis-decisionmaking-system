package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.models.Monitoring;
import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.model.models.TIARequest;
import com.ftn.sbnz.service.simulation.MonitoringSimulacija;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TIAService {
	private final KorisnikService korisnikService;

	private final MonitoringSimulacija monitoringSimulacija = new MonitoringSimulacija();
	public void utvrdiNivoRizikaOdMU(final TIARequest tiaRequest) {
		final Pacijent pacijent = korisnikService.getPacijentByJmbg(tiaRequest.getJmbgPacijenta());
		final Integer ABCD2Skor = izracunajABCD2Skor(tiaRequest, pacijent);
	}

	public Integer izracunajABCD2Skor(final TIARequest tiaRequest, final Pacijent pacijent) {
		int skor = 0;
		final Monitoring monitoring = monitoringSimulacija.simulirajMerenjePritiska();
		if ((monitoring.getPritisak().getSistolni() > 140) || (monitoring.getPritisak().getDijastolni() > 90)) skor += 1;
		if (pacijent.dobaviGodine() > 60) skor += 1;
		if (tiaRequest.isHemipareza() || tiaRequest.isHemiplegija()) skor += 2;
		if (tiaRequest.isSmetnjeGovora()) skor += 1;
		if (tiaRequest.getTrajanjeSimptoma() >= 60) skor += 2;
		else if (tiaRequest.getTrajanjeSimptoma() > 10 && tiaRequest.getTrajanjeSimptoma() < 60) skor += 1;
		if (tiaRequest.isDijabetes()) skor += 1;
		return skor;
	}
}
