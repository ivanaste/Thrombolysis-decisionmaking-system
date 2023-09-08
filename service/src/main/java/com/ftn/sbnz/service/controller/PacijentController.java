package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.service.services.korisnik.PacijentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pacijent")
@RequiredArgsConstructor
public class PacijentController {
    private final PacijentService pacijentService;


    @GetMapping
    public List<Pacijent> dobaviSve() {
        return pacijentService.dobaviSve();
    }

    @DeleteMapping("/{id}")
    public List<Pacijent> obrisiPacijenta(@PathVariable("id") UUID id) {
        pacijentService.obrisiPoId(id);
        return pacijentService.dobaviSve();
    }
}
