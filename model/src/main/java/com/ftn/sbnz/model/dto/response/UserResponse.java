package com.ftn.sbnz.model.dto.response;

import lombok.*;

import javax.validation.constraints.Email;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    UUID id;
    @Email
    String email;
    String role;
}
