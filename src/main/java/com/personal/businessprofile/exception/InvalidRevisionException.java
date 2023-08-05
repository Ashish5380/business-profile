package com.personal.businessprofile.exception;

import com.personal.businessprofile.exception.base.ServiceException;
import org.springframework.http.HttpStatus;

public class InvalidRevisionException extends ServiceException {
    public InvalidRevisionException() {
    }

    public InvalidRevisionException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public InvalidRevisionException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
