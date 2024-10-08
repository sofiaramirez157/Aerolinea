package com.example.Aerolinea.flight;

import com.example.Aerolinea.dto.FlightRequestDTO;
import com.example.Aerolinea.dto.FlightResponseDTO;
import com.example.Aerolinea.exceptions.InvalidRequestException;
import com.example.Aerolinea.exceptions.ResourceNotFoundException;
import com.example.Aerolinea.model.Flight;
import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.repository.IDestinationRepository;
import com.example.Aerolinea.repository.IFlightRepository;
import com.example.Aerolinea.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FlightServiceUnitTest {

    @Mock
    private IFlightRepository iFlightRepository;

    @Mock
    private IDestinationRepository destinationRepository;

    @InjectMocks
    private FlightService flightService;

    private Flight flight;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        flight = new Flight();
        flight.setId(1L);
        flight.setOrigin("Madrid");
        flight.setDepartureTime(LocalTime.of(8, 15));
        flight.setArrivalTime(LocalTime.of(10, 20));
        flight.setAvailableSeats(60);
        flight.setStatus(true);

        Destination destination = new Destination();
        destination.setId(1L);
        destination.setCountry("Spain");
        destination.setCode("MAD");
        flight.setDestination(destination);
    }

    @Test
    void createFlight() {
        FlightRequestDTO flightRequestDTO = new FlightRequestDTO();
        flightRequestDTO.setOrigin(flight.getOrigin());
        flightRequestDTO.setDepartureTime(flight.getDepartureTime());
        flightRequestDTO.setArrivalTime(flight.getArrivalTime());
        flightRequestDTO.setAvailableSeats(flight.getAvailableSeats());
        flightRequestDTO.setDestinationId(1L);

        when(destinationRepository.findById(1L)).thenReturn(Optional.of(flight.getDestination()));
        when(iFlightRepository.save(ArgumentMatchers.any(Flight.class))).thenReturn(flight);

        FlightResponseDTO createdFlight = flightService.createFlight(flightRequestDTO);

        assertNotNull(createdFlight);
        assertEquals(flight.getId(), createdFlight.getId());
        assertEquals(flight.getOrigin(), createdFlight.getOrigin());
        assertEquals(flight.getDepartureTime(), createdFlight.getDepartureTime());
        assertEquals(flight.getArrivalTime(), createdFlight.getArrivalTime());
        assertEquals(flight.getAvailableSeats(), createdFlight.getAvailableSeats());
        assertEquals(flight.isStatus(), createdFlight.isStatus());
    }

    @Test
    void getAllFlights() {
        List<Flight> flightList = new ArrayList<>();
        flightList.add(flight);

        when(iFlightRepository.findAll()).thenReturn(flightList);

        List<FlightResponseDTO> flights = flightService.getAllFlights();

        assertNotNull(flights);
        assertEquals(1, flights.size());
        assertEquals(flight.getId(), flights.get(0).getId());
        assertEquals(flight.getOrigin(), flights.get(0).getOrigin());
    }

    @Test
    void getFlightById() {
        when(iFlightRepository.findById(1L)).thenReturn(Optional.of(flight));

        FlightResponseDTO foundFlight = flightService.getFlightById(flight.getId());

        assertNotNull(foundFlight);
        assertEquals(flight.getId(), foundFlight.getId());
        assertEquals(flight.getOrigin(), foundFlight.getOrigin());
    }

    @Test
    void updateFlight() {
        FlightRequestDTO flightRequestDTO = new FlightRequestDTO();
        flightRequestDTO.setOrigin("Barcelona");
        flightRequestDTO.setDepartureTime(LocalTime.of(8, 15));
        flightRequestDTO.setArrivalTime(LocalTime.of(10, 20));
        flightRequestDTO.setAvailableSeats(60);
        flightRequestDTO.setDestinationId(1L);

        when(iFlightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(destinationRepository.findById(1L)).thenReturn(Optional.of(flight.getDestination()));
        when(iFlightRepository.save(any(Flight.class))).thenReturn(flight);

        FlightResponseDTO result = flightService.updateFlight(flightRequestDTO, 1L);

        assertNotNull(result);
        assertEquals(flight.getId(), result.getId());
        assertEquals("Barcelona", result.getOrigin());
        assertEquals(flight.getDepartureTime(), result.getDepartureTime());
        assertEquals(flight.getArrivalTime(), result.getArrivalTime());
        assertEquals(flight.getAvailableSeats(), result.getAvailableSeats());
        assertEquals(flight.isStatus(), result.isStatus());
    }

    @Test
    void deleteFlightById() {
        when(iFlightRepository.existsById(flight.getId())).thenReturn(true);

        flightService.deleteFlightById(flight.getId());

        verify(iFlightRepository, times(1)).deleteById(flight.getId());
    }

    @Test
    void createFlight_invalidRequest() {
        FlightRequestDTO flightRequestDTO = new FlightRequestDTO();
        flightRequestDTO.setOrigin("");

        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            flightService.createFlight(flightRequestDTO);
        });

        assertEquals("Flight origin must not be empty.", exception.getMessage());
    }

    @Test
    void updateFlight_flightNotFound() {
        FlightRequestDTO flightRequestDTO = new FlightRequestDTO();
        flightRequestDTO.setOrigin("Barcelona");
        flightRequestDTO.setDepartureTime(LocalTime.of(8, 15));
        flightRequestDTO.setArrivalTime(LocalTime.of(10, 20));
        flightRequestDTO.setAvailableSeats(60);
        flightRequestDTO.setDestinationId(1L);

        when(iFlightRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            flightService.updateFlight(flightRequestDTO, 1L);
        });

        assertEquals("Flight not found with ID: 1", exception.getMessage());
    }
}