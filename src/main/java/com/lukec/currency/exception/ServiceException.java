package com.lukec.currency.exception;


import lombok.Getter;

public class ServiceException extends RuntimeException {

    @Getter
    private final ServiceExceptionCode exceptionCode;

    public enum ServiceExceptionCode {
        CONFLICT,
        INTERNAL_ERROR,
        NOT_FOUND,
        NO_CONTENT,
        VALIDATION_ERROR,
        UNAUTHORIZED,
        FORBIDDEN,
        OTHER_ERROR
    }

    public ServiceException(ServiceExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public ServiceException(ServiceExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public ServiceException(ServiceExceptionCode exceptionCode, String message, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }

}
