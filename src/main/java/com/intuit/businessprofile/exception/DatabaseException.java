package com.intuit.businessprofile.exception;

import com.intuit.businessprofile.exception.base.FatalException;
import org.springframework.http.HttpStatus;

public class DatabaseException extends FatalException {
    public DatabaseException() {
    }

    public DatabaseException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public DatabaseException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
