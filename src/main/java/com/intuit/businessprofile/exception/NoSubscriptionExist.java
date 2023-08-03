package com.intuit.businessprofile.exception;

import com.intuit.businessprofile.exception.base.ServiceException;
import org.springframework.http.HttpStatus;

public class NoSubscriptionExist extends ServiceException {
    public NoSubscriptionExist() {
    }

    public NoSubscriptionExist(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public NoSubscriptionExist(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
