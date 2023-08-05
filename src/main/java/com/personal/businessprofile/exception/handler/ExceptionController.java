package com.personal.businessprofile.exception.handler;

import com.personal.businessprofile.exception.base.FatalException;
import com.personal.businessprofile.exception.base.ServiceException;
import com.personal.businessprofile.exception.base.BaseException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Log4j2
@Order(Integer.MIN_VALUE)
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({FatalException.class})
    @ResponseBody
    public ResponseEntity<?> handleFatalException(final FatalException e) {
        String stackTrace = BaseException.getStackTraceInStringFmt(e);
        log.error("Message : {}, trace: {}", e.getMessage(), stackTrace);
        return new ResponseEntity<>(e.getMessage(), getStatus(e));
    }

    @ExceptionHandler({ServiceException.class})
    @ResponseBody
    public ResponseEntity<?> handleServiceException(final ServiceException e) {
        String stackTrace = BaseException.getStackTraceInStringFmt(e);
        log.error("Message : {}, trace: {}", e.getMessage(), stackTrace);
        return new ResponseEntity<>(e.getMessage(), getStatus(e));
    }

    private HttpStatus getStatus(BaseException exception) {
        return Optional.ofNullable(exception.getHttpStatus())
          .orElse(HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
