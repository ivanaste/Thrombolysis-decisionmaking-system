package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.request.LoginRequest;
import com.ftn.sbnz.model.dto.request.RegistrationRequest;
import com.ftn.sbnz.model.dto.response.AuthTokenResponse;
import com.ftn.sbnz.model.dto.response.UserResponse;
import com.ftn.sbnz.service.services.auth.GetSelf;
import com.ftn.sbnz.service.services.auth.LogInUser;
import com.ftn.sbnz.service.services.auth.RegisterNewUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.KeyStoreException;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LogInUser loginUser;
    private final GetSelf getSelf;
    private final RegisterNewUser registerNewUser;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(@Valid @RequestBody final LoginRequest loginRequest) {
        return loginUser.execute(loginRequest);
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        registerNewUser.execute(registrationRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/self")
    public UserResponse getSelf() {
        return getSelf.execute();
    }

}
