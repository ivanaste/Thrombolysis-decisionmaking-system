package com.ftn.sbnz.service.services.auth;

import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.service.exceptions.UnauthorizedException;
import com.ftn.sbnz.service.services.user.GetUserById;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetLoggedInUser {
    private final GetUserById getUserById;

    public Korisnik execute() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException();
        }

        // Getting the user from the DB, because the user from the auth principal
        // is not a managed object, so some lazy collections (assigned items) fail
        // to be fetched.
        final UUID userId = ((Korisnik) authentication.getPrincipal()).getId();
        return getUserById.execute(userId);
    }
}
