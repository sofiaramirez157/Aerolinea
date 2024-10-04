package com.example.Aerolinea.unitTest.service;

import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.repositories.IDestinationRepository;
import com.example.Aerolinea.service.DestinationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DestinationServiceUnitTest {
    @Mock
    private IDestinationRepository iDestinationRepository;

    @InjectMocks
    private DestinationService destinationService;
    private Destination destination;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        destination = new Destination();
        destination.setId(1L);
        destination.setName("Paris");
        destination.setCountry("Francia");
    }
    @Test
    void createDestinationTest() {
        when(iDestinationRepository.save(ArgumentMatchers.any(Destination.class))).thenReturn(destination);

        Destination createdDestination = destinationService.createDestination(destination);

        assertNotNull(createdDestination);
        assertEquals(destination.getId(), createdDestination.getId());
        assertEquals(destination.getName(), createdDestination.getName());
        assertEquals(destination.getCountry(), createdDestination.getCountry());
    }

    @Test
    void getAllDestinationTest() {
        List<Destination> destinationList = new ArrayList<>();
        destinationList.add(destination);

        when(iDestinationRepository.findAll()).thenReturn(destinationList);

        List<Destination> destinations = destinationService.getAllDestination();

        assertNotNull(destinations);
        assertEquals(1, destinations.size());
        assertEquals(destination.getId(), destinations.get(0).getId());
    }

    @Test
    void getDestinationByIdTest() {
        when(iDestinationRepository.findById(1L)).thenReturn(Optional.of(destination));

        Optional<Destination> foundDestination = destinationService.getDestinationById(destination.getId());

        assertTrue(foundDestination.isPresent());
        assertEquals(destination.getId(), foundDestination.get().getId());
    }
    @Test
    void updateDestinationTest() {
        Destination updatedDestination = new Destination();
        updatedDestination.setId(1L);
        updatedDestination.setName("Roma");
        updatedDestination.setCountry("Italia");

        when(iDestinationRepository.findById(1L)).thenReturn(Optional.of(updatedDestination));

        Destination result = destinationService.updateDestination(updatedDestination, 1L);

        assertNotNull(result);
        assertEquals(updatedDestination.getId(), result.getId());
        assertEquals(updatedDestination.getName(), result.getName());
        assertEquals(updatedDestination.getCountry(), result.getCountry());
    }

    @Test
    void deleteDestinationTest() {
        when(iDestinationRepository.existsById(destination.getId())).thenReturn(true);

        boolean isDelete = destinationService.deleteDestination(destination.getId());

        assertTrue(isDelete);
        verify(iDestinationRepository, times(1)).deleteById(destination.getId());
    }
}
