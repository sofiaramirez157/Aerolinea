package com.example.Aerolinea.unitTest.controller;

import com.example.Aerolinea.controller.DestinationController;
import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.service.DestinationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DestinationControllerUnitTest {
    @Mock
    private DestinationService destinationService;

    @InjectMocks
    private DestinationController destinationController;

    private MockMvc mockMvc;
    private Destination destination;
    private List<Destination> destinationList;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        destination = new Destination();
        destination.setId(1L);
        destination.setName("Madrid");
        destination.setCountry("España");
    }

    @Test
    void createDestinationTest() throws Exception{
        when(destinationService.createDestination(any(Destination.class))).thenReturn(destination);

        Destination response = destinationController.createDestination(destination);

        assertEquals(destination.getId(),response.getId());
        assertEquals(destination.getName(), response.getName());
        assertEquals(destination.getCountry(),response.getCountry());
    }

    @Test
    void getAllDestinationTest() {
        when(destinationService.getAllDestination()).thenReturn(Collections.singletonList(destination));

        List<Destination> response = destinationController.getAllDestination();

        assertEquals(destination.getId(), response.get(0).getId());
        assertEquals(destination.getName(), response.get(0).getName());
        assertEquals(destination.getCountry(),response.get(0).getCountry());

    }

    @Test
    void getDestinationIdTest() {
        when(destinationService.getDestinationById(1L)).thenReturn(Optional.of(destination));

        Optional<Destination> response = destinationController.getDestinationId(1L);

        assertTrue(response.isPresent());
        assertEquals(destination.getId(), response.get().getId());
        assertEquals(destination.getName(), response.get().getName());
        assertEquals(destination.getCountry(), response.get().getCountry());
    }

    @Test
    void updateDestinationTest() {
        Destination updatedDestination = new Destination();
        updatedDestination.setId(1L);
        updatedDestination.setName("New Paris");
        updatedDestination.setCountry("France");

        when(destinationService.updateDestination(updatedDestination, 1L)).thenReturn(updatedDestination);

        Destination response = destinationController.updateDestination(updatedDestination, 1L);

        assertEquals(updatedDestination.getId(), response.getId());
        assertEquals(updatedDestination.getName(), response.getName());
        assertEquals(updatedDestination.getCountry(), response.getCountry());
    }

    @Test
    void deleteDestinationByIdTest() {
        when(destinationService.deleteDestination(1L)).thenReturn(true);

        String response = destinationController.deleteDestinationById(1L);

        assertEquals("Destination with id 1 was delete", response);
    }
}
