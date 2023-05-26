package com.ftn.sbnz.service.services.auth;

import com.ftn.sbnz.model.dto.request.LoginRequest;
import com.ftn.sbnz.model.dto.response.AuthTokenResponse;
import com.ftn.sbnz.model.models.Person;
import com.ftn.sbnz.service.configProperties.CustomProperties;
import com.ftn.sbnz.service.services.jwt.JwtGenerateToken;
import com.ftn.sbnz.service.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogInUser {
    private final AuthenticationManager authenticationManager;
    private final GetUserByEmail getUserByEmail;
    private final CustomProperties customProperties;
    private final JwtGenerateToken jwtGenerateToken;

    public ResponseEntity<AuthTokenResponse> execute(final LoginRequest loginRequest) {
        final Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (final Exception e) {
            throw new BadCredentialsException("Bad login credentials");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        final Person user = getUserByEmail.execute(userDetails.getUsername());
        String authToken = jwtGenerateToken.execute(user.getEmail(), customProperties.getAuthTokenExpirationMilliseconds(), user.getRole().toString());
        return ResponseEntity.status(HttpStatus.OK).body(new AuthTokenResponse(authToken));
    }
}
