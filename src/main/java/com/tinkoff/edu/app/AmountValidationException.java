package com.tinkoff.edu.app;

public class AmountValidationException extends IllegalArgumentException {
    public AmountValidationException() {
    }

    public AmountValidationException(String s) {
        super(s);
    }

    public AmountValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AmountValidationException(Throwable cause) {
        super(cause);
    }
}