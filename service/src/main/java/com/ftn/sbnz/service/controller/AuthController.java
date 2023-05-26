package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.request.LoginRequest;
import com.ftn.sbnz.model.dto.response.AuthTokenResponse;
import com.ftn.sbnz.model.dto.response.UserResponse;
import com.ftn.sbnz.service.services.auth.GetSelf;
import com.ftn.sbnz.service.services.auth.LogInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LogInUser loginUser;
    private final GetSelf getSelf;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(@Valid @RequestBody final LoginRequest loginRequest) {
        return loginUser.execute(loginRequest);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/self")
    public UserResponse getSelf() {
        return getSelf.execute();
    }

}
