package com.example.Aerolinea.destination;

import com.example.Aerolinea.controller.DestinationController;
import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.service.DestinationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
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
        destination.setName("Madrid");
        destination.setCountry("Spain");

        destinationList = Arrays.asList(destination);
    }

    @Test
    void createDestinationTest() {
        when(destinationService.createDestination(any(Destination.class))).thenReturn(destination);

        Destination response = destinationController.createDestination(destination);

        assertEquals(destination.getId(), response.getId());
        assertEquals(destination.getName(), response.getName());
        assertEquals(destination.getCountry(), response.getCountry());
    }

    @Test
    void getAllDestinationTest() {
        when(destinationService.getAllDestination()).thenReturn(destinationList);

        List<Destination> response = destinationController.getAllDestination();

        assertEquals(1, response.size());
        assertEquals(destination.getId(), response.get(0).getId());
        assertEquals(destination.getName(), response.get(0).getName());
        assertEquals(destination.getCountry(), response.get(0).getCountry());
    }

    @Test
    void getDestinationByIdTest() {
        when(destinationService.getDestinationById(1L)).thenReturn(destination);

        Destination response = destinationController.getDestinationById(1L);

        assertNotNull(response);
        assertEquals(destination.getId(), response.getId());
        assertEquals(destination.getName(), response.getName());
        assertEquals(destination.getCountry(), response.getCountry());
    }

    @Test
    void updateDestinationTest() {
        Destination updatedDestination = new Destination();
        updatedDestination.setId(1L);
        updatedDestination.setName("Paris");
        updatedDestination.setCountry("France");

        destinationController.updateDestination(updatedDestination, 1L);

        verify(destinationService).updateDestination(updatedDestination, 1L);
    }

    @Test
    void deleteDestinationByIdTest() {
        when(destinationService.deleteDestination(1L)).thenReturn(true);

        String response = destinationController.deleteDestinationById(1L);

        assertEquals("Destination with id 1 was deleted", response);
    }
}