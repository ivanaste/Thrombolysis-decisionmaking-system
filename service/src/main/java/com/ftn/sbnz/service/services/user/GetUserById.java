package com.ftn.sbnz.service.services.user;

import com.ftn.sbnz.model.models.Person;
import com.ftn.sbnz.service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserById {
    private final PersonRepository personRepository;

    @Transactional(readOnly = true)
    public Person execute(UUID id) {
        return personRepository.getReferenceById(id);
    }

}
