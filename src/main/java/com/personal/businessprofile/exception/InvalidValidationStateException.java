package com.personal.businessprofile.exception;

import com.personal.businessprofile.exception.base.ServiceException;
import org.springframework.http.HttpStatus;

public class InvalidValidationStateException extends ServiceException {
    public InvalidValidationStateException() {
    }

    public InvalidValidationStateException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public InvalidValidationStateException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
