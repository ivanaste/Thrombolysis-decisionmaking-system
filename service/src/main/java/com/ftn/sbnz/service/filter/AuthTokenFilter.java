package com.ftn.sbnz.service.filter;

import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.service.services.jwt.JwtValidateAndGetUsername;
import com.ftn.sbnz.service.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtValidateAndGetUsername jwtValidateAndGetUsername;
    private final GetUserByEmail getUserByEmail;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
            ServletException, IOException {
        final String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.substring("Bearer ".length());
        final String username = jwtValidateAndGetUsername.execute(token);

        if (username == null) {
            filterChain.doFilter(request, response);
            return;
        }

        final Korisnik user = getUserByEmail.execute(username);
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, List.of(new SimpleGrantedAuthority("DOCTOR")));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
