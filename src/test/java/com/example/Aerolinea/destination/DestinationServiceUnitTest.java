package com.example.Aerolinea.destination;

import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.repository.IDestinationRepository;
import com.example.Aerolinea.service.DestinationService;
import com.example.Aerolinea.exceptions.DestinationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DestinationServiceUnitTest {

    @Mock
    private IDestinationRepository iDestinationRepository;

    @InjectMocks
    private DestinationService destinationService;

    private Destination destination;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        destination = new Destination();
        destination.setId(1L);
        destination.setCountry("France");
        destination.setCode("FR");
    }

    @Test
    void createDestinationTest() {
        when(iDestinationRepository.save(ArgumentMatchers.any(Destination.class))).thenReturn(destination);

        Destination createdDestination = destinationService.createDestination(destination);

        assertNotNull(createdDestination);
        assertEquals(destination.getId(), createdDestination.getId());
        assertEquals(destination.getCountry(), createdDestination.getCountry());
        assertEquals(destination.getCode(), createdDestination.getCode());
    }

    @Test
    void getAllDestinationsTest() {
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

        Destination foundDestination = destinationService.getDestinationById(destination.getId());

        assertNotNull(foundDestination);
        assertEquals(destination.getId(), foundDestination.getId());
        assertEquals(destination.getCountry(), foundDestination.getCountry());
        assertEquals(destination.getCode(), foundDestination.getCode());
    }

    @Test
    void updateDestinationTest() {
        when(iDestinationRepository.existsById(1L)).thenReturn(true);
        when(iDestinationRepository.findById(1L)).thenReturn(Optional.of(destination));

        Destination updatedDestination = new Destination();
        updatedDestination.setId(1L);
        updatedDestination.setCountry("Spain");
        updatedDestination.setCode("ES");

        when(iDestinationRepository.save(any(Destination.class))).thenReturn(updatedDestination);

        Destination result = destinationService.updateDestination(updatedDestination, 1L);

        verify(iDestinationRepository).save(argThat(savedDestination ->
                savedDestination.getId() == 1L &&
                        "Spain".equals(savedDestination.getCountry()) &&
                        "ES".equals(savedDestination.getCode())
        ));

        assertNotNull(result);
        assertEquals("Spain", result.getCountry());
        assertEquals("ES", result.getCode());
    }

    @Test
    void updateDestinationNotFoundTest() {
        Destination updatedDestination = new Destination();
        updatedDestination.setId(2L);
        updatedDestination.setCountry("Nowhere");
        updatedDestination.setCode("NW");

        when(iDestinationRepository.existsById(2L)).thenReturn(false);

        assertThrows(DestinationNotFoundException.class, () -> {
            destinationService.updateDestination(updatedDestination, 2L);
        });
    }

    @Test
    void deleteDestinationTest() {
        when(iDestinationRepository.existsById(destination.getId())).thenReturn(true);

        boolean isDeleted = destinationService.deleteDestination(destination.getId());

        assertTrue(isDeleted);
        verify(iDestinationRepository, times(1)).deleteById(destination.getId());
    }

    @Test
    void deleteDestinationNotFoundTest() {
        when(iDestinationRepository.existsById(destination.getId())).thenReturn(false);

        assertThrows(DestinationNotFoundException.class, () -> {
            destinationService.deleteDestination(destination.getId());
        });
    }
}