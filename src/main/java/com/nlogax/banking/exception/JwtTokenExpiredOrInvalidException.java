package com.nlogax.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class JwtTokenExpiredOrInvalidException extends RuntimeException {

    public JwtTokenExpiredOrInvalidException() {
        super();
    }

    public JwtTokenExpiredOrInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenExpiredOrInvalidException(String message) {
        super(message);
    }

    public JwtTokenExpiredOrInvalidException(Throwable cause) {
        super(cause);
    }
}
