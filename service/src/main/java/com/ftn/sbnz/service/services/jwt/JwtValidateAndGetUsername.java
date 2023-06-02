package com.ftn.sbnz.service.services.jwt;

import com.ftn.sbnz.service.configProperties.CustomProperties;
import com.ftn.sbnz.service.exceptions.AuthTokenExpiredException;
import com.ftn.sbnz.service.exceptions.AuthTokenInvalidException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtValidateAndGetUsername {
    private final CustomProperties customProperties;

    public String execute(final String token) {
        try {
            return Jwts.parser().setSigningKey(customProperties.getJwtSecret().getBytes()).parseClaimsJws(token).getBody().getSubject();
        } catch (final ExpiredJwtException ex) {
            throw new AuthTokenExpiredException();
        } catch (final SignatureException | MalformedJwtException | UnsupportedJwtException |
                       IllegalArgumentException ex) {
            throw new AuthTokenInvalidException();
        }
    }
}
