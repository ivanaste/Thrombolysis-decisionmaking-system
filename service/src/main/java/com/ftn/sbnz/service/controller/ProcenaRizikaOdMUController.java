package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.request.TIARequest;
import com.ftn.sbnz.service.services.ProcenaRizikaOdMUService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProcenaRizikaOdMUController {

	private final ProcenaRizikaOdMUService procenaRizikaOdMUService;

	@PostMapping(value = "/rizikOdMU", produces = "application/json")
	public String utvrdjivanjeNivoaRizikaOdMU(@RequestBody final TIARequest tiaRequest) {

		return procenaRizikaOdMUService.utvrdiNivoRizikaOdMU(tiaRequest);
	}

}
