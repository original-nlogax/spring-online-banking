package com.nlogax.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserDoesntExistException extends RuntimeException {

    public UserDoesntExistException() {
        super();
    }

    public UserDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDoesntExistException(String message) {
        super(message);
    }

    public UserDoesntExistException(Throwable cause) {
        super(cause);
    }
}
