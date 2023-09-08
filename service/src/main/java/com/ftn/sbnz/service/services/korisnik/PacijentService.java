package com.ftn.sbnz.service.services.korisnik;

import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.model.models.Role;
import com.ftn.sbnz.service.repository.PacijentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PacijentService {

    private final PacijentRepository pacijentRepository;

    @Transactional
    public Pacijent getPacijentByJmbg(String jmbg) {
        return pacijentRepository.getPacijentByJmbg(jmbg);
    }

    @Transactional
    public Pacijent sacuvajPacijenta(String jmbg, LocalDate datumRodjenja) {
        Pacijent pacijent = pacijentRepository.getPacijentByJmbg(jmbg);
        if (Objects.isNull(pacijent)) {
            pacijent = new Pacijent();
            pacijent.setJmbg(jmbg);
            pacijent.setDatumRodjenja(datumRodjenja);
            pacijent.setRole(Role.PATIENT);
            return pacijentRepository.save(pacijent);
        }
        return pacijent;
    }

    @Transactional
    public void obrisiPoId(UUID id) {
        pacijentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Pacijent> dobaviSve() {
        return pacijentRepository.findAll();
    }
}
