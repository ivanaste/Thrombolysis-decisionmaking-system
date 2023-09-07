package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.service.services.korisnik.PacijentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pacijent")
@RequiredArgsConstructor
public class PacijentController {
    private final PacijentService pacijentService;

    
    @GetMapping
    public List<Pacijent> dobaviSve() {
        return pacijentService.dobaviSve();
    }
}
