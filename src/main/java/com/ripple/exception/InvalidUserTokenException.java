package com.ripple.exception;

public class InvalidUserTokenException extends RuntimeException{
    public InvalidUserTokenException(String message) {
        super(message);
    }
}
