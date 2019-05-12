package com.ripple.exception;

/**
 * Created by intel on 5/11/2019.
 */
public class InvalidUserTokenException extends RuntimeException{
    public InvalidUserTokenException(String message) {
        super(message);
    }
}
