package com.ftn.sbnz.service.exceptions;

public class AuthTokenInvalidException extends CustomRuntimeException {
    public AuthTokenInvalidException() {
        super(ExceptionKeys.AUTH_TOKEN_INVALID);
    }
}
