package com.ftn.sbnz.service.services.jwt;

import com.ftn.sbnz.service.configProperties.CustomProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtGenerateToken {
    private final CustomProperties customProperties;

    public String execute(final String email, final long expirationMilliseconds, String role) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + expirationMilliseconds))
                .claim("role", role)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, customProperties.getJwtSecret().getBytes())
                .compact();
    }
}
