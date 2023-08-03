package com.intuit.businessprofile.exception;

import com.intuit.businessprofile.exception.base.ServiceException;
import org.springframework.http.HttpStatus;

public class ValidationFailureException extends ServiceException {
    public ValidationFailureException() {
    }

    public ValidationFailureException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public ValidationFailureException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
