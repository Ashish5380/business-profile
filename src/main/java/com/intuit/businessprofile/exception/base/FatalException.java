package com.intuit.businessprofile.exception.base;

import com.intuit.businessprofile.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class FatalException extends BaseException {
    public FatalException() {
    }

    public FatalException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public FatalException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
