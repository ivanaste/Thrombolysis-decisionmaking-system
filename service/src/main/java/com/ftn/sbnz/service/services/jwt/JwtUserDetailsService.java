package com.ftn.sbnz.service.services.jwt;

import com.ftn.sbnz.model.models.Person;
import com.ftn.sbnz.service.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final GetUserByEmail getUserByEmail;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Person user = getUserByEmail.execute(username);


        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("DOCTOR")));
    }
}

