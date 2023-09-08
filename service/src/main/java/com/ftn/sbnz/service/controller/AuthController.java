package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.request.ChangePasswordRequest;
import com.ftn.sbnz.model.dto.request.LoginRequest;
import com.ftn.sbnz.model.dto.request.RegistrationRequest;
import com.ftn.sbnz.model.dto.response.AuthTokenResponse;
import com.ftn.sbnz.model.dto.response.UserResponse;
import com.ftn.sbnz.service.services.auth.ChangePassword;
import com.ftn.sbnz.service.services.auth.GetSelf;
import com.ftn.sbnz.service.services.auth.LogInUser;
import com.ftn.sbnz.service.services.auth.RegisterNewUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LogInUser loginUser;
    private final GetSelf getSelf;
    private final RegisterNewUser registerNewUser;
    private final ChangePassword changePassword;


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(@Valid @RequestBody final LoginRequest loginRequest) {
        return loginUser.execute(loginRequest);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('NURSE','ADMIN')")
    public void register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        registerNewUser.execute(registrationRequest);
    }

    @PutMapping("/passwordChange")
    public void changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordDTO) {
        changePassword.execute(changePasswordDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/self")
    public UserResponse getSelf() {
        return getSelf.execute();
    }

}
