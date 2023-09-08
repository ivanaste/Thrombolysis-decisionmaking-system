package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.request.RegistrationRequest;
import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.service.services.korisnik.KorisnikService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/korisnik")
@RequiredArgsConstructor
public class KorisnikController {
    private final KorisnikService korisnikService;


    @GetMapping("/{email}")
    public Korisnik dobaviKorisnika(@PathVariable String email) {
        return korisnikService.getKorisnikByEmail(email);
    }

    @PutMapping()
    public Korisnik izmeniKorisnika(@RequestBody RegistrationRequest registrationRequest) {
        return korisnikService.izmeniKorisnika(registrationRequest);
    }

    @GetMapping
    public List<Korisnik> dobaviSve() {
        return korisnikService.dobaviSve();
    }

    @DeleteMapping("/{id}")
    public List<Korisnik> obrisiKorisnika(@PathVariable("id") UUID id) {
        korisnikService.obrisiPoId(id);
        return korisnikService.dobaviSve();
    }
}
