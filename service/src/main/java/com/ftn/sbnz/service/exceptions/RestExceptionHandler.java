package com.ftn.sbnz.service.exceptions;

import com.ftn.sbnz.service.translations.Translator;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({
    })
    protected ResponseEntity<?> handleBadRequestExceptions(CustomRuntimeException ex) {
        return buildResponseEntity(new ApiException(Translator.toLocale(ex.getKey()), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({
            AuthTokenExpiredException.class,
            AuthTokenInvalidException.class,
            UnauthorizedException.class,
    })
    protected ResponseEntity<?> handleUnauthorizedExceptions(CustomRuntimeException ex) {
        return buildResponseEntity(new ApiException(Translator.toLocale(ex.getKey()), HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler({AccessDeniedException.class})
    protected ResponseEntity<?> handleAccessDeniedException() {
        return buildResponseEntity(new ApiException(Translator.toLocale(ExceptionKeys.INSUFFICIENT_PERMISSIONS), HttpStatus.FORBIDDEN));
    }

    @ExceptionHandler({
            RoleNotFoundException.class,
            UserNotFoundException.class,
    })
    protected ResponseEntity<?> handleNotFoundExceptions(CustomRuntimeException ex) {
        return buildResponseEntity(new ApiException(Translator.toLocale(ex.getKey()), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<?> handleBadCredentialsException() {
        return buildResponseEntity(new ApiException(Translator.toLocale(ExceptionKeys.BAD_LOGIN_CREDENTIALS), HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    protected ResponseEntity<?> handleInsufficientAuthenticationException() {
        return buildResponseEntity(new ApiException(Translator.toLocale(ExceptionKeys.MISSING_AUTHENTICATION), HttpStatus.UNAUTHORIZED));
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getDefaultMessage());
        }

        for (ObjectError err : ex.getBindingResult().getGlobalErrors()) {
            errors.add(err.getObjectName() + " " + err.getDefaultMessage());
        }

        String errorMessage = String.join(", ", errors);

        return (ResponseEntity<Object>) buildResponseEntity(new ApiException(errorMessage, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<?> defaultExceptionHandler(Throwable t) {
        logger.error("Unhandled exception: " + Strings.join(Arrays.asList(t.getStackTrace()), '\n'));
        return buildResponseEntity(new ApiException("Unhandled exception", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<?> buildResponseEntity(final ApiException err) {
        return new ResponseEntity<>(err, err.getStatus());
    }

}
