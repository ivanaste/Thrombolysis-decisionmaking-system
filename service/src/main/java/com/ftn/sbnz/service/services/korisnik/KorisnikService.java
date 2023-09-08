package com.ftn.sbnz.service.services.korisnik;

import com.ftn.sbnz.model.dto.request.RegistrationRequest;
import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.model.models.Role;
import com.ftn.sbnz.service.repository.KorisnikRepository;
import com.ftn.sbnz.service.repository.PacijentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;

    @Transactional
    public Korisnik getKorisnikByEmail(String email) {
        return korisnikRepository.findByEmail(email);
    }

    @Transactional
    public Korisnik izmeniKorisnika(RegistrationRequest registrationRequest) {
        Korisnik korisnik = getKorisnikByEmail(registrationRequest.getEmail());
        korisnik.setName(registrationRequest.getIme());
        korisnik.setSurname(registrationRequest.getPrezime());
        if (korisnik.getRole().equals(Role.PATIENT)) {
            ((Pacijent) korisnik).setDatumRodjenja(registrationRequest.getDatumRodjenja());
        }
        return korisnikRepository.save(korisnik);
    }

}
