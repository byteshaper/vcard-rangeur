package com.byteshaper.vcardrangeur.exception;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = -752902960124821130L;

    public NotFoundException(String message) {
        super(message);
    }
}
