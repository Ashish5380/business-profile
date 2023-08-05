package com.personal.businessprofile.exception.base;

import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.io.StringWriter;

public class BaseException extends RuntimeException {
    HttpStatus httpStatus;

    public BaseException() {
    }

    public BaseException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public BaseException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public static String getStackTraceInStringFmt(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
