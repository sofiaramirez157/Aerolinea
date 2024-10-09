package com.example.Aerolinea.exceptions;

import java.io.Serial;

public class FlightNotAvailableException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public FlightNotAvailableException(String message) {
        super(message);
    }

    public FlightNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}