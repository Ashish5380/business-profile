package com.intuit.businessprofile.exception;

import com.intuit.businessprofile.exception.base.FatalException;
import org.springframework.http.HttpStatus;

/**
 * @author sridharswain
 */
public class CacheOperationException extends FatalException {

    private String key;

    public CacheOperationException(String key) {
        this.key = key;
    }

    public CacheOperationException(String message, String key) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
        this.key = key;
    }

    public CacheOperationException(String message, Throwable cause, String key) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, cause);
        this.key = key;
    }
}
