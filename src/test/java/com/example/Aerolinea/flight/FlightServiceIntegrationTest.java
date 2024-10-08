package com.example.Aerolinea.flight;

import com.example.Aerolinea.dto.FlightRequestDTO;
import com.example.Aerolinea.dto.FlightResponseDTO;
import com.example.Aerolinea.exceptions.ResourceNotFoundException;
import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.model.Flight;
import com.example.Aerolinea.repositories.IDestinationRepository;
import com.example.Aerolinea.repositories.IFlightRepository;
import com.example.Aerolinea.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FlightServiceIntegrationTest {

    @Mock
    private IFlightRepository flightRepository;

    @Mock
    private IDestinationRepository destinationRepository;

    @InjectMocks
    private FlightService flightService;

    private Flight flight1;
    private Flight flight2;
    private Destination destination1;
    private Destination destination2;
    private final List<Flight> flightList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        destination1 = new Destination(1L, "Scotland", "SCO");
        destination2 = new Destination(2L, "Madrid", "MAD");

        flight1 = new Flight();
        flight1.setId(1L);
        flight1.setOrigin("Madrid");
        flight1.setDestination(destination1);
        flight1.setDepartureTime(LocalTime.of(8, 15));
        flight1.setArrivalTime(LocalTime.of(10, 20));
        flight1.setAvailableSeats(60);
        flight1.setStatus(true);

        flight2 = new Flight();
        flight2.setId(2L);
        flight2.setOrigin("Scotland");
        flight2.setDestination(destination2);
        flight2.setDepartureTime(LocalTime.of(15, 0));
        flight2.setArrivalTime(LocalTime.of(20, 35));
        flight2.setAvailableSeats(0);
        flight2.setStatus(false);

        flightList.add(flight1);
        flightList.add(flight2);
    }

    @Test
    void createFlight() {
        FlightRequestDTO flightRequestDTO = new FlightRequestDTO();
        flightRequestDTO.setOrigin("Madrid");
        flightRequestDTO.setDestinationId(1L);
        flightRequestDTO.setDepartureTime(LocalTime.of(8, 15));
        flightRequestDTO.setArrivalTime(LocalTime.of(10, 20));
        flightRequestDTO.setAvailableSeats(60);
        flightRequestDTO.setStatus(true);

        when(destinationRepository.findById(flightRequestDTO.getDestinationId())).thenReturn(Optional.of(destination1));
        when(flightRepository.save(any(Flight.class))).thenAnswer(invocation -> {
            Flight flightToSave = invocation.getArgument(0);
            flightToSave.setId(3L);
            return flightToSave;
        });

        FlightResponseDTO createdFlight = flightService.createFlight(flightRequestDTO);

        assertNotNull(createdFlight);
        assertEquals(3L, createdFlight.getId());
        assertEquals("Madrid", createdFlight.getOrigin());
        assertEquals("Scotland", createdFlight.getDestination().getCountry());
        assertEquals(LocalTime.of(8, 15), createdFlight.getDepartureTime());
        assertEquals(LocalTime.of(10, 20), createdFlight.getArrivalTime());
        assertEquals(60, createdFlight.getAvailableSeats());
        assertTrue(createdFlight.isStatus());
    }

    @Test
    void getAllFlights() {
        when(flightRepository.findAll()).thenReturn(flightList);

        List<FlightResponseDTO> result = flightService.getAllFlights();

        assertEquals(2, result.size());
        assertEquals("Madrid", result.get(0).getOrigin());
        assertEquals("Scotland", result.get(1).getOrigin());
    }

    @Test
    void getFlightById() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight1));

        FlightResponseDTO flightResponseDTO = flightService.getFlightById(1L);

        assertNotNull(flightResponseDTO);
        assertEquals("Madrid", flightResponseDTO.getOrigin());
    }

    @Test
    void updateFlight() {
        FlightRequestDTO flightRequestDTO = new FlightRequestDTO();
        flightRequestDTO.setOrigin("Updated Origin");
        flightRequestDTO.setDestinationId(1L);
        flightRequestDTO.setDepartureTime(LocalTime.of(8, 15));
        flightRequestDTO.setArrivalTime(LocalTime.of(10, 20));
        flightRequestDTO.setAvailableSeats(30);
        flightRequestDTO.setStatus(true);

        when(flightRepository.findById(flight1.getId())).thenReturn(Optional.of(flight1));
        when(destinationRepository.findById(flightRequestDTO.getDestinationId())).thenReturn(Optional.of(destination1));
        when(flightRepository.save(any(Flight.class))).thenReturn(flight1);

        FlightResponseDTO updatedFlight = flightService.updateFlight(flightRequestDTO, flight1.getId());

        assertNotNull(updatedFlight);
        assertEquals(1L, updatedFlight.getId());
        assertEquals("Updated Origin", updatedFlight.getOrigin());
        assertEquals(LocalTime.of(8, 15), updatedFlight.getDepartureTime());
        assertEquals(LocalTime.of(10, 20), updatedFlight.getArrivalTime());
        assertEquals(30, updatedFlight.getAvailableSeats());
        assertTrue(updatedFlight.isStatus());

        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    void deleteFlight() {
        when(flightRepository.existsById(2L)).thenReturn(true);
        doNothing().when(flightRepository).deleteById(2L);

        flightService.deleteFlightById(2L);

        verify(flightRepository, times(1)).deleteById(2L);
    }

    @Test
    void deleteFlight_notFound() {
        when(flightRepository.existsById(2L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> flightService.deleteFlightById(2L));
    }
}