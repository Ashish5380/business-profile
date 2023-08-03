package com.intuit.businessprofile.exception;

import com.intuit.businessprofile.exception.base.ServiceException;
import org.springframework.http.HttpStatus;

public class ResourceOccupiedException extends ServiceException {
    public ResourceOccupiedException() {
    }

    public ResourceOccupiedException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public ResourceOccupiedException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
