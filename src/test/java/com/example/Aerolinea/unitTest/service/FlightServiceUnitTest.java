package com.example.Aerolinea.unitTest.service;

import com.example.Aerolinea.controller.DestinationController;
import com.example.Aerolinea.controller.FlightController;
import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.model.Flight;
import com.example.Aerolinea.repositories.IDestinationRepository;
import com.example.Aerolinea.repositories.IFlightRepository;
import com.example.Aerolinea.service.DestinationService;
import com.example.Aerolinea.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FlightServiceUnitTest {

    @Mock
    private IFlightRepository iFlightRepository;

    @InjectMocks
    private FlightService flightService;
    private MockMvc mockMvc;
    private Flight flight;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(flightService).build();
        flight = new Flight();
        flight.setId(1L);
        flight.setOrigin("Madrid");
        flight.setDepartureTime(LocalTime.of(8, 15));
        flight.setArrivalTime(LocalTime.of(10, 20));
        flight.setAvailableSeats(60);
        flight.setStatus(true);
    }

    @Test
    void createFlight() throws Exception{
        when(iFlightRepository.save(ArgumentMatchers.any(Flight.class))).thenReturn(flight);

        Flight createdFlight = flightService.createFlight(flight);

        assertNotNull(createdFlight);
        assertEquals(flight.getId(),createdFlight.getId());
        assertEquals(flight.getOrigin(), createdFlight.getOrigin());
        assertEquals(flight.getDepartureTime(),createdFlight.getDepartureTime());
        assertEquals(flight.getArrivalTime(),createdFlight.getArrivalTime());
        assertEquals(flight.getAvailableSeats(),createdFlight.getAvailableSeats());
        assertEquals(flight.isStatus(),createdFlight.isStatus());
    }

    @Test
    void getAllFlight() {
        List<Flight> flightList = new ArrayList<>();
        flightList.add(flight);

        when(iFlightRepository.findAll()).thenReturn(flightList);

        List<Flight> flights = flightService.getAllFlight();

        assertNotNull(flight);
        assertEquals(1, flights.size());
        assertEquals(flight.getId(), flights.get(0).getId());
    }

    @Test
    void getFlightById() {
        when(iFlightRepository.findById(1L)).thenReturn(Optional.of(flight));

        Optional<Flight> foundFlight = flightService.getFlightById(flight.getId());

        assertTrue(foundFlight.isPresent());
        assertEquals(flight.getId(), foundFlight.get().getId());
    }

    @Test
    void updateFlight() {
        Flight updatedFlight = new Flight();
        updatedFlight.setId(1L);
        updatedFlight.setOrigin("Barcelona");
        updatedFlight.setDepartureTime(LocalTime.of(8, 15));
        updatedFlight.setArrivalTime(LocalTime.of(10, 20));
        updatedFlight.setAvailableSeats(60);
        updatedFlight.setStatus(true);

        when(iFlightRepository.findById(1L)).thenReturn(Optional.of(updatedFlight));

        Flight result = flightService.updateFlight(updatedFlight, 1L);

        assertNotNull(result);
        assertEquals(updatedFlight.getId(),result.getId());
        assertEquals(updatedFlight.getOrigin(), result.getOrigin());
        assertEquals(updatedFlight.getDepartureTime(),result.getDepartureTime());
        assertEquals(updatedFlight.getArrivalTime(),result.getArrivalTime());
        assertEquals(updatedFlight.getAvailableSeats(),result.getAvailableSeats());
        assertEquals(updatedFlight.isStatus(),result.isStatus());
    }

    @Test
    void deleteFlight() {
        when(iFlightRepository.existsById(flight.getId())).thenReturn(true);

        boolean isDelete = flightService.deleteFlight(flight.getId());

        assertTrue(isDelete);
        verify(iFlightRepository, times(1)).deleteById(flight.getId());
    }

}
