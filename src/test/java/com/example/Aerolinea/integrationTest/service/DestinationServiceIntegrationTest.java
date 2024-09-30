package com.example.Aerolinea.integrationTest.service;

import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.repositories.IDestinationRepository;
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
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        destination1 = new Destination();
        destination1.setId(1);
        destination1.setName("Paris");
        destination1.setCountry("Francia");

        destination2 = new Destination();
        destination2.setId(2);
        destination2.setName("Madrid");
        destination2.setCountry("España");
    }

    @Test
    void createDestination() throws Exception{
        when(iDestinationRepository.save(any(Destination.class))).thenReturn(destination2);
        Destination newDestination = destinationService.createDestination(destination2);

        assertNotNull(newDestination);
        assertNotNull(2, String.valueOf(newDestination.getId()));
        assertNotNull("Madrid", newDestination.getName());
        assertNotNull("España", newDestination.getCountry());

        verify(iDestinationRepository, times(1)).save(destination2);
    }

    @Test
    void getAllDestination(){
        List<Destination> destinationList = new ArrayList<>();
        destinationList.add(destination1);
        destinationList.add(destination2);

        when(iDestinationRepository.findAll()).thenReturn(destinationList);

        List<Destination> allDestination = destinationService.getAllDestination();

        assertNotNull(allDestination);
        assertEquals(2, allDestination.size());
        assertTrue(allDestination.contains(destination1));
        assertTrue(allDestination.contains(destination2));

        verify(iDestinationRepository, times(1)).findAll();
    }

    @Test
    void getDestinationByIdTest(){
        when(iDestinationRepository.findById(1L)).thenReturn(Optional.of(destination1));

        Optional<Destination> foundDestination = destinationService.getDestinationById(1);

        assertTrue(foundDestination.isPresent());
        assertEquals("Paris", foundDestination.get().getName());
        assertEquals("Francia", foundDestination.get().getCountry());
    }

    @Test
    void updateDestination(){
        Destination destination = new Destination();

        when(iDestinationRepository.findById(1L)).thenReturn(Optional.of(destination));

        destination.setName("Madrid");
        destination.setCountry("Spain");

        Destination updateDestination = iDestinationRepository.save(destination);

        verify(iDestinationRepository).save(destination);
    }

    @Test
    void deleteDestination(){
        long id = 2;
        iDestinationRepository.deleteById(id);
        verify(iDestinationRepository).deleteById(id);
    }
}
