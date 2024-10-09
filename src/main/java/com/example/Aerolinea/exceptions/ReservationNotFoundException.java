package com.example.Aerolinea.exceptions;

import java.io.Serial;

public class ReservationNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ReservationNotFoundException(String message) {
        super(message);
    }

    public ReservationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}