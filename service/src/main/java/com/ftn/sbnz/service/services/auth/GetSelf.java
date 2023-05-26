package com.ftn.sbnz.service.services.auth;

import com.ftn.sbnz.model.dto.response.UserResponse;
import com.ftn.sbnz.model.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSelf {
    private final GetLoggedInUser getLoggedInUser;

    public UserResponse execute() {
        Person person = getLoggedInUser.execute();
        return UserResponse.builder()
                .email(person.getEmail())
                .id(person.getId())
                .build();
    }
}
