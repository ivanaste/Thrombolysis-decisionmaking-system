package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.models.TIARequest;
import com.ftn.sbnz.service.services.TIAService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TIAController {

	private final TIAService tiaService;

	@PostMapping(value = "/rizikOdMU", produces = "application/json")
	public void utvrdjivanjeNivoaRizikaOdMU(@RequestBody final TIARequest tiaRequest) {

		tiaService.utvrdiNivoRizikaOdMU(tiaRequest);
	}

}
