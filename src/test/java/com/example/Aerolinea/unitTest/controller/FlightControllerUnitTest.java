package com.example.Aerolinea.unitTest.controller;

import com.example.Aerolinea.controller.FlightController;
import com.example.Aerolinea.model.Flight;
import com.example.Aerolinea.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FlightControllerUnitTest {

    @Mock
    private FlightService flightService;
    @InjectMocks
    private FlightController flightController;
    private MockMvc mockMvc;

    private Flight flight;
    private List<Flight> flightList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();

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
        when(flightService.createFlight(any(Flight.class))).thenReturn(flight);

        Flight response = flightController.createFlight(flight);

        assertEquals(flight.getId(),response.getId());
        assertEquals(flight.getOrigin(), response.getOrigin());
        assertEquals(flight.getDepartureTime(),response.getDepartureTime());
        assertEquals(flight.getArrivalTime(),response.getArrivalTime());
        assertEquals(flight.getAvailableSeats(),response.getAvailableSeats());
        assertEquals(flight.isStatus(),response.isStatus());
    }

    @Test
    void getAllFlight() {
        when(flightService.getAllFlight()).thenReturn(Collections.singletonList(flight));

        List<Flight> response = flightController.getAllFlight();

        assertEquals(flight.getId(),response.get(0).getId());
        assertEquals(flight.getOrigin(), response.get(0).getOrigin());
        assertEquals(flight.getDepartureTime(),response.get(0).getDepartureTime());
        assertEquals(flight.getArrivalTime(),response.get(0).getArrivalTime());
        assertEquals(flight.getAvailableSeats(),response.get(0).getAvailableSeats());
        assertEquals(flight.isStatus(),response.get(0).isStatus());
    }

    @Test
    void getFlightId() {
        when(flightService.getFlightById(1L)).thenReturn(Optional.of(flight));

        Optional<Flight> response = flightController.getFlightById(1L);

        assertTrue(response.isPresent());
        assertEquals(flight.getId(),response.get().getId());
        assertEquals(flight.getOrigin(), response.get().getOrigin());
        assertEquals(flight.getDepartureTime(),response.get().getDepartureTime());
        assertEquals(flight.getArrivalTime(),response.get().getArrivalTime());
        assertEquals(flight.getAvailableSeats(),response.get().getAvailableSeats());
        assertEquals(flight.isStatus(),response.get().isStatus());
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

        when(flightService.updateFlight(updatedFlight, 1L)).thenReturn(updatedFlight);

        Flight response = flightController.updateFlight(updatedFlight, 1L);

        assertEquals(updatedFlight.getId(),response.getId());
        assertEquals(updatedFlight.getOrigin(), response.getOrigin());
        assertEquals(updatedFlight.getDepartureTime(),response.getDepartureTime());
        assertEquals(updatedFlight.getArrivalTime(),response.getArrivalTime());
        assertEquals(updatedFlight.getAvailableSeats(),response.getAvailableSeats());
        assertEquals(updatedFlight.isStatus(),response.isStatus());
    }

    @Test
    void deleteFlightById() {
        when(flightService.deleteFlight(1L)).thenReturn(true);

        String response = flightController.deleteFlightById(1L);

        assertEquals("Flight with id 1 was deleted", response);
    }
}
