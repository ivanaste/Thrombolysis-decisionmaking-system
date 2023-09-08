package com.ftn.sbnz.service.services.user;

import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.service.repository.KorisnikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserById {
    private final KorisnikRepository korisnikRepository;

    @Transactional(readOnly = true)
    public Korisnik execute(UUID id) {
        return korisnikRepository.findById(id).get();
    }

}
