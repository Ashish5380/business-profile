package com.intuit.businessprofile.exception;

import com.intuit.businessprofile.exception.base.ServiceException;
import org.springframework.http.HttpStatus;

public class InvalidProductException extends ServiceException {
    public InvalidProductException() {
    }

    public InvalidProductException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public InvalidProductException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
