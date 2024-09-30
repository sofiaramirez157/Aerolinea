package com.example.Aerolinea.exceptions;

public class DestinationNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DestinationNotFoundException(String message) {
        super(message);
    }

    public DestinationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}