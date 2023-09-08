package com.ftn.sbnz.service.services.auth;

import com.ftn.sbnz.model.dto.request.RegistrationRequest;
import com.ftn.sbnz.model.models.EmailDetails;
import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.model.models.Role;
import com.ftn.sbnz.service.repository.PersonRepository;
import com.ftn.sbnz.service.services.mail.SendMail;
import com.ftn.sbnz.service.translations.Codes;
import com.ftn.sbnz.service.translations.Translator;
import lombok.RequiredArgsConstructor;
import org.kie.api.task.model.Email;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyStoreException;
import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class RegisterNewUser {
    private final PasswordEncoder passwordEncoder;
    private final GeneratePassword generatePassword;
    private final PersonRepository personRepository;
    private final SendMail sendMail;

    public Korisnik execute(RegistrationRequest registrationRequest) {
        final String password = generatePassword.execute();
        Korisnik korisnik;

        if (registrationRequest.getRole().equals(Role.PATIENT)) {
            korisnik = new Pacijent(
                    registrationRequest.getEmail(),
                    passwordEncoder.encode(password),
                    registrationRequest.getIme(),
                    registrationRequest.getPrezime(),
                    registrationRequest.getRole(),
                    registrationRequest.getJmbg(),
                    registrationRequest.getDatumRodjenja()
            );
        } else {
            korisnik = Korisnik.builder()
                    .email(registrationRequest.getEmail())
                    .name(registrationRequest.getIme())
                    .surname(registrationRequest.getPrezime())
                    .role(registrationRequest.getRole())
                    .password(passwordEncoder.encode(password))
                    .build();
        }

                EmailDetails emailDetails = new EmailDetails(korisnik.getEmail(), Translator.toLocale(
                Codes.SIGNUP_PASSWORD, new String[]{password}), "USPESNA REGISTRACIJA");
        sendMail.execute(emailDetails);

        return personRepository.save(korisnik);
    }

}
