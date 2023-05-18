package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.request.NIHHSRequest;
import com.ftn.sbnz.model.dto.request.NastanakSimptomaRequest;
import com.ftn.sbnz.model.dto.request.NeuroloskiPregledRequest;
import com.ftn.sbnz.service.services.OdlukaService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OdlukaController {

	private final OdlukaService sampleService;

	@PostMapping(value = "/nastanakSimptoma", produces = "application/json")
	public String proveraOdlukeNaOsnovuNastankaSimptoma(@RequestBody NastanakSimptomaRequest nastanakSimptomaRequest) {

		return sampleService.proveriOdlukuNaOsnovuNastankaSimptoma(nastanakSimptomaRequest);
	}

	@PostMapping(value = "/neuroloskiPregled", produces = "application/json")
	public String proveraOdlukeNaOsnovuNeuroloskogPregled(@RequestBody NeuroloskiPregledRequest neuroloskiPregledRequest) {

		return sampleService.proveriOdlukuNaOsnovuNeuroloskogPregleda(neuroloskiPregledRequest);
	}

	@PostMapping(value = "/nihhsSkor", produces = "application/json")
	public String proveraOdlukeNaOsnovuNIHHSSkora(@RequestBody NIHHSRequest nihhsRequest) {

		return sampleService.proveriOdlukuNaOsnovuNIHHSSkora(nihhsRequest);
	}
}
