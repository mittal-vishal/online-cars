package com.intuit.craft.onlinecars.exception;

import lombok.Getter;

@Getter
public class ExceptionCode {

    private final String code;
    private final String defaultMessage;
    private final boolean isRetryable;

    public ExceptionCode(String code, String defaultMessage, boolean isRetryable) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.isRetryable = isRetryable;
    }
}
