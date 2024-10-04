package com.example.Aerolinea.integrationTest.service;

import com.example.Aerolinea.model.Flight;
import com.example.Aerolinea.repositories.IFlightRepository;
import com.example.Aerolinea.service.FlightService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class FlightServiceIntegrationTest {
    @Mock private IFlightRepository iFlightRepository;
    @InjectMocks private FlightService flightService;

    private Flight flight1;
    private Flight flight2;
    private List<Flight> flightList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        flight1 = new Flight();
        flight1.setId(1);
        flight1.setOrigin("Madrid");
        flight1.setDepartureTime(LocalTime.of(8, 15));
        flight1.setArrivalTime(LocalTime.of(10, 20));
        flight1.setAvailableSeats(60);
        flight1.setStatus(true);

        flight2 = new Flight();
        flight2.setId(2);
        flight2.setOrigin("Scotland");
        flight2.setDepartureTime(LocalTime.of(15, 00));
        flight2.setArrivalTime(LocalTime.of(20, 35));
        flight2.setAvailableSeats(0);
        flight2.setStatus(false);

        flightList.add(flight1);
        flightList.add(flight2);

    }

    @Test
    void createFlight() {
        when(iFlightRepository.save(ArgumentMatchers.any(Flight.class))).thenReturn(flight2);

        Flight result = flightService.createFlight(flight2);
        assertEquals(2, result.getId());
        assertEquals("Scotland", result.getOrigin());
        assertEquals(LocalTime.of(15, 00), result.getDepartureTime());
        assertEquals(LocalTime.of(20, 35), result.getArrivalTime());
        assertEquals(0, result.getAvailableSeats());
        assertEquals(false, result.isStatus());
    }

    @Test
    void getAllFlight() {
        when(iFlightRepository.findAll()).thenReturn(flightList);

        List<Flight> result = flightService.getAllFlight();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Madrid", result.get(0).getOrigin());
        assertEquals(LocalTime.of(8, 15), result.get(0).getDepartureTime());
        assertEquals(LocalTime.of(10, 20), result.get(0).getArrivalTime());
        assertEquals(60, result.get(0).getAvailableSeats());
        assertEquals(true, result.get(0).isStatus());

        assertEquals(2, result.get(1).getId());
        assertEquals("Scotland", result.get(1).getOrigin());
        assertEquals(LocalTime.of(15, 00), result.get(1).getDepartureTime());
        assertEquals(LocalTime.of(20, 35), result.get(1).getArrivalTime());
        assertEquals(0, result.get(1).getAvailableSeats());
        assertEquals(false, result.get(1).isStatus());
    }

    @Test
    void getFlightById() {
        when(iFlightRepository.findById(1L)).thenReturn(Optional.of(flight1));

        Optional<Flight> flightId = flightService.getFlightById(1);

        assertEquals("Madrid", flightId.get().getOrigin());
    }

    @Test
    void updateFlight() {
         when(iFlightRepository.save(any(Flight.class))).thenReturn(flight1);
    Flight update = flight1;
    update.setAvailableSeats(30);

    flightService.updateFlight(update, 1);
    assertEquals(1, update.getId());
    assertEquals("Madrid", update.getOrigin());
    assertEquals(LocalTime.of(8, 15), update.getDepartureTime());
    assertEquals(LocalTime.of(10, 20), update.getArrivalTime());
    assertEquals(30, update.getAvailableSeats());
    assertEquals(true, update.isStatus());
    

    verify(iFlightRepository, times(1)).save(update);

    }

    @Test
    void deleteFlight() {
        when(iFlightRepository.findById(2L)).thenReturn(Optional.of(flight2));
    
        flightService.deleteFlight(2);
    
        verify(iFlightRepository, times(1)).deleteById(2L);      
    }

}
