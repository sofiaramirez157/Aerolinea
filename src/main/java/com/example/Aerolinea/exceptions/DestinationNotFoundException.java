package com.example.Aerolinea.exceptions;

public class DestinationNotFoundException extends RuntimeException {
    public DestinationNotFoundException(String message) {
        super(message);
    }

    public DestinationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}