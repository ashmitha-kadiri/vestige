package com.vestige.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class VendorNotVerifiedException extends RuntimeException {
    public VendorNotVerifiedException(String message) {
        super(message);
    }
}
