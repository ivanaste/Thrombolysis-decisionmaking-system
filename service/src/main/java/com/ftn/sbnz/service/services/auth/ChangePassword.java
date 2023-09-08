package com.ftn.sbnz.service.services.auth;

import com.ftn.sbnz.model.dto.request.ChangePasswordRequest;
import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.service.repository.KorisnikRepository;
import com.ftn.sbnz.service.services.jwt.JwtValidateAndGetUsername;
import com.ftn.sbnz.service.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangePassword {

    private final JwtValidateAndGetUsername jwtValidateAndGetUsername;
    private final GetUserByEmail getUserByEmail;
    private final KorisnikRepository korisnikRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = false, rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void execute(ChangePasswordRequest changePasswordDTO) {
        final String username = jwtValidateAndGetUsername.execute(changePasswordDTO.getAuthToken());
        final Korisnik user = getUserByEmail.execute(username);
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        korisnikRepository.save(user);
    }

}
