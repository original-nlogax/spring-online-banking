package com.nlogax.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountDoesntExistException extends RuntimeException {

    public AccountDoesntExistException() {
        super();
    }

    public AccountDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountDoesntExistException(String message) {
        super(message);
    }

    public AccountDoesntExistException(Throwable cause) {
        super(cause);
    }
}
