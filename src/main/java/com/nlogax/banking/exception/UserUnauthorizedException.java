package com.nlogax.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UserUnauthorizedException extends RuntimeException {

    public UserUnauthorizedException() {
        super();
    }

    public UserUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserUnauthorizedException(String message) {
        super(message);
    }

    public UserUnauthorizedException(Throwable cause) {
        super(cause);
    }
}
