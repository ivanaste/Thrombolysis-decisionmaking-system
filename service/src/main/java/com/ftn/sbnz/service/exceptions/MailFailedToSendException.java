package com.ftn.sbnz.service.exceptions;

public class MailFailedToSendException extends CustomRuntimeException {
    public MailFailedToSendException() {
        super(ExceptionKeys.MAIL_FAILED);
    }
}
