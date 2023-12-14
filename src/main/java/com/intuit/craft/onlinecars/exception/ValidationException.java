package com.intuit.craft.onlinecars.exception;

public class ValidationException extends ServiceException {
    private static final long serialVersionUID = 1L;

    public ValidationException(String errorMessage) {
        super(errorMessage);
    }

    public ValidationException(ExceptionCode exceptionCode, String errorMessage) {
        super(exceptionCode, errorMessage);
    }
}
