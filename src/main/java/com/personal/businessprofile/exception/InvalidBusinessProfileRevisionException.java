package com.personal.businessprofile.exception;

import com.personal.businessprofile.exception.base.ServiceException;
import org.springframework.http.HttpStatus;

public class InvalidBusinessProfileRevisionException extends ServiceException {
    public InvalidBusinessProfileRevisionException() {
    }

    public InvalidBusinessProfileRevisionException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public InvalidBusinessProfileRevisionException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
