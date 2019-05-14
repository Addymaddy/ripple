package com.ripple.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by intel on 5/11/2019.
 */
public class EmailNotUniqueException extends RuntimeException {

    public EmailNotUniqueException(String message) {
        super(message);
    }
}
