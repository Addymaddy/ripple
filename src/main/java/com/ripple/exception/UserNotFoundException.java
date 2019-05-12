package com.ripple.exception;

/**
 * Created by intel on 5/11/2019.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
