package com.example.Aerolinea.exceptions;

public class FlightNotAvailableException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FlightNotAvailableException(String message) {
        super(message);
    }

    public FlightNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}