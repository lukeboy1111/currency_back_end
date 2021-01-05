package com.lukec.currency.controller;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.lukec.currency.exception.handler.ApiException;
import com.lukec.currency.exception.ServiceException;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionController {
	Logger logger = Logger.getLogger(ExceptionController.class.getName());

    @ExceptionHandler(ServiceException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ResponseEntity<ApiException> handleServiceException(ServiceException ex, WebRequest request) {
        ApiException apiException = new ApiException(ex.getMessage());
        return new ResponseEntity<>(apiException, getHttpStatusCode(ex.getExceptionCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleException(Exception ex, WebRequest request) {
        logger.log(Level.SEVERE, ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException("Technical exception. Please contact your system administrator.");
        return new ResponseEntity<>(apiException, httpStatus);
    }

    private HttpStatus getHttpStatusCode(ServiceException.ServiceExceptionCode code) {
        switch (code) {
            case CONFLICT:
                return HttpStatus.CONFLICT;
            case NO_CONTENT:
                return HttpStatus.NO_CONTENT;
            case NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            case UNAUTHORIZED:
                return HttpStatus.UNAUTHORIZED;
            case VALIDATION_ERROR:
                return HttpStatus.BAD_REQUEST;
            case FORBIDDEN:
                return HttpStatus.FORBIDDEN;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
