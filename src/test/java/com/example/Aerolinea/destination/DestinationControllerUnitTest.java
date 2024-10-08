package com.example.Aerolinea.destination;

import com.example.Aerolinea.controller.DestinationController;
import com.example.Aerolinea.exceptions.DestinationNotFoundException;
import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.service.DestinationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DestinationControllerUnitTest {

    @Mock
    private DestinationService destinationService;

    @InjectMocks
    private DestinationController destinationController;

    private Destination destination;
    private List<Destination> destinationList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        destination = new Destination();
        destination.setId(1L);
        destination.setCountry("Spain");
        destination.setCode("ES");

        destinationList = Collections.singletonList(destination);
    }

    @Test
    void createDestinationTest() {
        when(destinationService.createDestination(any(Destination.class))).thenReturn(destination);

        ResponseEntity<Destination> response = destinationController.createDestination(destination);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(destination.getId(), response.getBody().getId());
        assertEquals(destination.getCountry(), response.getBody().getCountry());
        assertEquals(destination.getCode(), response.getBody().getCode());
    }

    @Test
    void getAllDestinationTest() {
        when(destinationService.getAllDestination()).thenReturn(destinationList);

        ResponseEntity<List<Destination>> response = destinationController.getAllDestination();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(destination.getId(), response.getBody().get(0).getId());
        assertEquals(destination.getCountry(), response.getBody().get(0).getCountry());
        assertEquals(destination.getCode(), response.getBody().get(0).getCode());
    }

    @Test
    void getDestinationByIdTest() {
        when(destinationService.getDestinationById(1L)).thenReturn(destination);

        ResponseEntity<Destination> response = destinationController.getDestinationById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(destination.getId(), response.getBody().getId());
        assertEquals(destination.getCountry(), response.getBody().getCountry());
        assertEquals(destination.getCode(), response.getBody().getCode());
    }

    @Test
    void updateDestinationTest() {
        Destination updatedDestination = new Destination();
        updatedDestination.setId(1L);
        updatedDestination.setCountry("France");
        updatedDestination.setCode("FR");

        when(destinationService.updateDestination(any(Destination.class), any(Long.class))).thenReturn(updatedDestination);

        ResponseEntity<Void> response = destinationController.updateDestination(updatedDestination, 1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(destinationService).updateDestination(updatedDestination, 1L);
    }

    @Test
    void deleteDestinationByIdTest() {
        when(destinationService.deleteDestination(1L)).thenReturn(true);

        ResponseEntity<String> response = destinationController.deleteDestinationById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Destination with ID 1 was deleted.", response.getBody());
    }

    @Test
    void deleteDestinationNotFoundTest() {
        when(destinationService.deleteDestination(1L)).thenThrow(new DestinationNotFoundException("Destination not found with ID: 1"));

        ResponseEntity<String> response = destinationController.deleteDestinationById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Destination not found with ID: 1", response.getBody());
    }
}