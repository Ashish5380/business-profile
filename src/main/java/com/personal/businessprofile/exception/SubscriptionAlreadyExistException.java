package com.personal.businessprofile.exception;

import com.personal.businessprofile.exception.base.ServiceException;
import org.springframework.http.HttpStatus;

public class SubscriptionAlreadyExistException extends ServiceException {
    public SubscriptionAlreadyExistException() {
    }

    public SubscriptionAlreadyExistException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public SubscriptionAlreadyExistException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
