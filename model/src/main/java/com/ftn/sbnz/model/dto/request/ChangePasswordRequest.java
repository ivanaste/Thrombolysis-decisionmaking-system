package com.ftn.sbnz.model.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordRequest {
    private String newPassword;
    private String newPasswordConfirmation;
    private String authToken;
}
