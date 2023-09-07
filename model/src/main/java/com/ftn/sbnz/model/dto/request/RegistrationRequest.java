package com.ftn.sbnz.model.dto.request;

import com.ftn.sbnz.model.models.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;


@Data
@NoArgsConstructor
public class RegistrationRequest {
    private String email;
    private Role role;
    private String jmbg;
    private String ime;
    private String prezime;
    private LocalDate datumRodjenja;
}
