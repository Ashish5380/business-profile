package com.intuit.businessprofile.exception.base;

import com.intuit.businessprofile.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class ServiceException extends BaseException {
    public ServiceException() {
    }

    public ServiceException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public ServiceException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
