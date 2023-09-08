package com.ftn.sbnz.service.services.korisnik;

import com.ftn.sbnz.model.dto.request.RegistrationRequest;
import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.model.models.Role;
import com.ftn.sbnz.service.repository.KorisnikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    @Transactional(readOnly = true)
    public List<Korisnik> dobaviSve() {
        return this.korisnikRepository.findAllByRoleIn(List.of(Role.DOCTOR, Role.NURSE));
    }

    @Transactional
    public void obrisiPoId(UUID id) {
        this.korisnikRepository.deleteById(id);
    }

}
