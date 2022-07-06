package com.nlogax.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserUnauthenticatedException extends RuntimeException {

    public UserUnauthenticatedException() {
        super();
    }

    public UserUnauthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserUnauthenticatedException(String message) {
        super(message);
    }

    public UserUnauthenticatedException(Throwable cause) {
        super(cause);
    }
}
