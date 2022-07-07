package com.nlogax.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TransactionDoesntExistException extends RuntimeException {

    public TransactionDoesntExistException() {
        super();
    }

    public TransactionDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionDoesntExistException(String message) {
        super(message);
    }

    public TransactionDoesntExistException(Throwable cause) {
        super(cause);
    }
}
