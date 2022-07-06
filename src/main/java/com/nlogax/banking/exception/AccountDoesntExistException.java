package com.nlogax.banking.exception;

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
