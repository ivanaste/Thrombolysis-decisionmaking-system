package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.request.ProcenaRizikaOdMURequest;
import com.ftn.sbnz.service.services.procena_rizika_od_MU.ProcenaRizikaOdMUService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProcenaRizikaOdMUController {

	private final ProcenaRizikaOdMUService procenaRizikaOdMUService;

	@PostMapping(value = "/rizikOdMU", produces = "application/json")
	public String utvrdjivanjeNivoaRizikaOdMU(@RequestBody final ProcenaRizikaOdMURequest procenaRizikaOdMURequest) {

		return procenaRizikaOdMUService.utvrdiNivoRizikaOdMU(procenaRizikaOdMURequest);
	}

	@PostMapping(value = "/rizikOdMUTemplejt", produces = "application/json")
	public String utvrdjivanjeNivoaRizikaOdMUTemplejt(@RequestBody final ProcenaRizikaOdMURequest procenaRizikaOdMURequest) {

		return procenaRizikaOdMUService.utvrdiNivoRizikaTemplejt(procenaRizikaOdMURequest);
	}

}
