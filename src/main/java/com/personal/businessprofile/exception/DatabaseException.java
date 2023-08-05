package com.personal.businessprofile.exception;

import com.personal.businessprofile.exception.base.FatalException;
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
