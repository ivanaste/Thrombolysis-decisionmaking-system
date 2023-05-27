package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.request.NIHHSRequest;
import com.ftn.sbnz.model.dto.request.NastanakSimptomaRequest;
import com.ftn.sbnz.model.dto.request.NeuroloskiPregledRequest;
import com.ftn.sbnz.model.models.Odluka;
import com.ftn.sbnz.service.services.donosenje_odluke_o_trombolizi.OdlukaOTromboliziService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odluka")
@RequiredArgsConstructor
public class OdlukaOTromboliziController {

    private final OdlukaOTromboliziService odlukaOTromboliziService;

    @PostMapping(value = "/nastanakSimptoma", produces = "application/json")
    public Odluka proveraOdlukeNaOsnovuNastankaSimptoma(@RequestBody NastanakSimptomaRequest nastanakSimptomaRequest) {

        return odlukaOTromboliziService.proveriOdlukuNaOsnovuNastankaSimptoma(nastanakSimptomaRequest);
    }

    @PostMapping(value = "/neuroloskiPregled", produces = "application/json")
    public String proveraOdlukeNaOsnovuNeuroloskogPregled(@RequestBody NeuroloskiPregledRequest neuroloskiPregledRequest) {

        return odlukaOTromboliziService.proveriOdlukuNaOsnovuNeuroloskogPregleda(neuroloskiPregledRequest);
    }

    @PostMapping(value = "/nihhsSkor", produces = "application/json")
    public String proveraOdlukeNaOsnovuNIHHSSkora(@RequestBody NIHHSRequest nihhsRequest) {

        return odlukaOTromboliziService.proveriOdlukuNaOsnovuNIHHSSkora(nihhsRequest);
    }

    @GetMapping(value = "/all")
    public List<Odluka> dobaviodluke() {
        return odlukaOTromboliziService.dobaviOdluke();
    }
}
