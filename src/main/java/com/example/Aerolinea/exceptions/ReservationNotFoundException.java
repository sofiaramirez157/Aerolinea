package com.example.Aerolinea.exceptions;

public class ReservationNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ReservationNotFoundException(String message) {
        super(message);
    }

    public ReservationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}