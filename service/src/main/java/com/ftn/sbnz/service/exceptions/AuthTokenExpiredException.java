package com.ftn.sbnz.service.exceptions;

public class AuthTokenExpiredException extends CustomRuntimeException {
	public AuthTokenExpiredException() {
		super(ExceptionKeys.AUTH_TOKEN_EXPIRED);
	}
}
