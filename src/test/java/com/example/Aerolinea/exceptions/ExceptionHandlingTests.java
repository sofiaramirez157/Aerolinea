package com.example.Aerolinea.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHandlingTests {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void shouldReturnMessageForResourceNotFoundException() {
        String message = "Resource not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void shouldReturnMessageForInvalidRequestException() {
        String message = "Invalid request";
        InvalidRequestException exception = new InvalidRequestException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void shouldReturnMessageForFlightNotAvailableException() {
        String message = "Flight not available";
        FlightNotAvailableException exception = new FlightNotAvailableException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void shouldReturnMessageForUserNotFoundException() {
        String message = "User not found";
        UserNotFoundException exception = new UserNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void shouldReturnMessageForReservationNotFoundException() {
        String message = "Reservation not found";
        ReservationNotFoundException exception = new ReservationNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void shouldReturnMessageForDestinationNotFoundException() {
        String message = "Destination not found";
        DestinationNotFoundException exception = new DestinationNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void shouldReturnMessageForUnauthorizedAccessException() {
        String message = "Unauthorized access";
        UnauthorizedAccessException exception = new UnauthorizedAccessException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void shouldHandleResourceNotFoundException() {
        String message = "Resource not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleResourceNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody(), "ErrorResponse should not be null");
        assertEquals(message, response.getBody().getMessage());
    }

    @Test
    public void shouldHandleInvalidRequestException() {
        String message = "Invalid request";
        InvalidRequestException exception = new InvalidRequestException(message);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInvalidRequestException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody(), "ErrorResponse should not be null");
        assertEquals(message, response.getBody().getMessage());
    }

    @Test
    public void shouldHandleFlightNotAvailableException() {
        String message = "Flight not available";
        FlightNotAvailableException exception = new FlightNotAvailableException(message);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleFlightNotAvailableException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody(), "ErrorResponse should not be null");
        assertEquals(message, response.getBody().getMessage());
    }

    @Test
    public void shouldHandleUserNotFoundException() {
        String message = "User not found";
        UserNotFoundException exception = new UserNotFoundException(message);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleUserNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody(), "ErrorResponse should not be null");
        assertEquals(message, response.getBody().getMessage());
    }

    @Test
    public void shouldHandleReservationNotFoundException() {
        String message = "Reservation not found";
        ReservationNotFoundException exception = new ReservationNotFoundException(message);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleReservationNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody(), "ErrorResponse should not be null");
        assertEquals(message, response.getBody().getMessage());
    }

    @Test
    public void shouldHandleDestinationNotFoundException() {
        String message = "Destination not found";
        DestinationNotFoundException exception = new DestinationNotFoundException(message);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleDestinationNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody(), "ErrorResponse should not be null");
        assertEquals(message, response.getBody().getMessage());
    }

    @Test
    public void shouldHandleUnauthorizedAccessException() {
        String message = "Unauthorized access";
        UnauthorizedAccessException exception = new UnauthorizedAccessException(message);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleUnauthorizedAccessException(exception);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody(), "ErrorResponse should not be null");
        assertEquals(message, response.getBody().getMessage());
    }

    @Test
    public void shouldHandleGenericException() {
        Exception exception = new Exception("Unexpected error");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody(), "ErrorResponse should not be null");
        assertEquals("An unexpected error occurred.", response.getBody().getMessage());
    }
}