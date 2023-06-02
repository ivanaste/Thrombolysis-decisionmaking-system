package com.ftn.sbnz.service.exceptions;

public class UnauthorizedException extends CustomRuntimeException {
	public UnauthorizedException() {
		super(ExceptionKeys.UNAUTHORIZED);
	}
}
