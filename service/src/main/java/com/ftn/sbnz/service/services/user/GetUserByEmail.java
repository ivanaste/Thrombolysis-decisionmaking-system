package com.ftn.sbnz.service.services.user;

import com.ftn.sbnz.model.models.Person;
import com.ftn.sbnz.service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetUserByEmail {
    private final PersonRepository userRepository;

    @Transactional(readOnly = true)
    public Person execute(final String email) {
        return userRepository.getByEmail(email);
    }
}
