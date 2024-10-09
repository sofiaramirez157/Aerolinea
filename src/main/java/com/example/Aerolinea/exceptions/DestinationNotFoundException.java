package com.example.Aerolinea.exceptions;

import java.io.Serial;

public class DestinationNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public DestinationNotFoundException(String message) {
        super(message);
    }

    public DestinationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}