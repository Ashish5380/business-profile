package com.intuit.businessprofile.exception;

import com.intuit.businessprofile.exception.base.ServiceException;
import org.springframework.http.HttpStatus;

public class InvalidBusinessProfileException extends ServiceException {
    public InvalidBusinessProfileException() {
    }

    public InvalidBusinessProfileException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public InvalidBusinessProfileException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
