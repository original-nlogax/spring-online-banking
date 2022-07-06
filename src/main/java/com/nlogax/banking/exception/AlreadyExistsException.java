package com.nlogax.banking.exception;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException() {
        super();
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
