package com.ripple.exception;

/**
 * Created by intel on 5/11/2019.
 */
public class VmNotFoundException extends RuntimeException {
    public VmNotFoundException(String message) {
        super(message);
    }
}
