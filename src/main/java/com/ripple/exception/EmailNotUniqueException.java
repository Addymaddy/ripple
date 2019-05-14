package com.ripple.exception;

public class EmailNotUniqueException extends RuntimeException {

    public EmailNotUniqueException(String message) {
        super(message);
    }
}
