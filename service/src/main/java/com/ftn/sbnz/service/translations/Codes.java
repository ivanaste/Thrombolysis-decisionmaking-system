package com.ftn.sbnz.service.translations;

public enum Codes implements Translation {
    PASSWORD_RESET_LINK("password_reset_link"),
    PASSWORD_RESET_EMAIL_SUBJECT("password_reset_email_subject"),
    PASSWORD_RESET_REQUEST_SUCCESS("password_reset_request_success"),
    EMAIL_ACTIVATION_SUCCESS("email_activation_success"),
    SIGN_UP_ACTIVATION_EMAIL_SUBJECT("sign_up_activation_email_subject"),
    SIGN_UP_ACTIVATION_EMAIL("sign_up_activation_email"),
    SEND_CERTIFICATE_SUBJECT("send_certificate_subject"),
    SEND_CERTIFICATE_MESSAGE("send_certificate_message"),
    CSR_REJECTION_SUBJECT("csr_rejection_subject"),
    CSR_REJECTION_MESSAGE("csr_rejection_message"),
    CERTIFICATE_REVOKED_SUBJECT("certificate_revoked_subject"),
    CERTIFICATE_REVOKED_MESSAGE("certificate_revoked_message"),
    LOGIN_PIN_SUBJECT("login_pin_subject"),
    LOGIN_PIN_MESSAGE("login_pin_message"),
    SIGN_UP_SET_PASSWORD_EMAIL("sign_up_set_password_email"),
    SIGN_UP_SET_PASSWORD_SUBJECT("sign_up_set_password_subject"),
    ALARM_RAD_SRCA("alarm_rad_srca"),
    ALARM_IREGULARAN_NIHHS("alarm_iregularan_nihhs");

    private final String code;

    Codes(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
