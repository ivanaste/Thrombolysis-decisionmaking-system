package com.ftn.sbnz.service.services.user;

import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.service.repository.KorisnikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetUserByEmail {
    private final KorisnikRepository userRepository;

    @Transactional(readOnly = true)
    public Korisnik execute(final String email) {
        return userRepository.findByEmail(email);
    }
}
