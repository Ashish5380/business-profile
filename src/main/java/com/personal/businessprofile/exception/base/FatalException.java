package com.personal.businessprofile.exception.base;

import org.springframework.http.HttpStatus;

public class FatalException extends BaseException {
    public FatalException() {
    }

    public FatalException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public FatalException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
