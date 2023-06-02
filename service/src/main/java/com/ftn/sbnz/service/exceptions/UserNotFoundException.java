package com.ftn.sbnz.service.exceptions;

public class UserNotFoundException extends CustomRuntimeException {
	public UserNotFoundException() {
		super(ExceptionKeys.USER_NOT_FOUND);
	}
}
