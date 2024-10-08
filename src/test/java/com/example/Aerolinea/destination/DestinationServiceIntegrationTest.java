package com.example.Aerolinea.destination;

import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.repository.IDestinationRepository;
import com.example.Aerolinea.service.DestinationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DestinationServiceIntegrationTest {
    @Mock
    private IDestinationRepository iDestinationRepository;

    @InjectMocks
    private DestinationService destinationService;

    private Destination destination1;
    private Destination destination2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        destination1 = new Destination();
        destination1.setId(1);
        destination1.setCountry("France");
        destination1.setCode("FR");

        destination2 = new Destination();
        destination2.setId(2);
        destination2.setCountry("Spain");
        destination2.setCode("ES");
    }

    @Test
    void createDestination() {
        when(iDestinationRepository.save(any(Destination.class))).thenReturn(destination2);
        Destination newDestination = destinationService.createDestination(destination2);

        assertNotNull(newDestination);
        assertEquals(2, newDestination.getId());
        assertEquals("Spain", newDestination.getCountry());
        assertEquals("ES", newDestination.getCode());

        verify(iDestinationRepository, times(1)).save(destination2);
    }

    @Test
    void getAllDestination() {
        List<Destination> destinationList = new ArrayList<>();
        destinationList.add(destination1);
        destinationList.add(destination2);

        when(iDestinationRepository.findAll()).thenReturn(destinationList);

        List<Destination> allDestinations = destinationService.getAllDestination();

        assertNotNull(allDestinations);
        assertEquals(2, allDestinations.size());
        assertTrue(allDestinations.contains(destination1));
        assertTrue(allDestinations.contains(destination2));

        verify(iDestinationRepository, times(1)).findAll();
    }

    @Test
    void getDestinationByIdTest() {
        when(iDestinationRepository.findById(1L)).thenReturn(Optional.of(destination1));

        Destination foundDestination = destinationService.getDestinationById(1);

        assertNotNull(foundDestination);
        assertEquals("France", foundDestination.getCountry());
        assertEquals("FR", foundDestination.getCode());
    }

    @Test
    void updateDestination() {
        when(iDestinationRepository.existsById(1L)).thenReturn(true);
        when(iDestinationRepository.findById(1L)).thenReturn(Optional.of(destination1));

        Destination updatedDestination = new Destination();
        updatedDestination.setCountry("Spain");
        updatedDestination.setCode("ES");

        when(iDestinationRepository.save(any(Destination.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Destination result = destinationService.updateDestination(updatedDestination, 1L);

        assertNotNull(result);
        assertEquals("Spain", result.getCountry());
        assertEquals("ES", result.getCode());

        verify(iDestinationRepository).existsById(1L);
        verify(iDestinationRepository).findById(1L);
        verify(iDestinationRepository).save(any(Destination.class));
    }

    @Test
    void deleteDestination() {
        long id = 2;
        when(iDestinationRepository.existsById(id)).thenReturn(true);

        boolean result = destinationService.deleteDestination(id);

        assertTrue(result);
        verify(iDestinationRepository).deleteById(id);
    }
}