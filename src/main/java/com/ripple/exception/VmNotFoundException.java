package com.ripple.exception;


public class VmNotFoundException extends RuntimeException {
    public VmNotFoundException(String message) {
        super(message);
    }
}
