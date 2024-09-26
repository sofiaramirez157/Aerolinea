package com.example.Aerolinea.integrationTest.controller;


import com.example.Aerolinea.controller.FlightController;
import com.example.Aerolinea.model.Flight;
import com.example.Aerolinea.service.FlightService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class FlightControllerIntegrationTest {
    @Mock
    private FlightService flightService;
    @InjectMocks
    private FlightController flightController;
    private MockMvc mockMvc;

    private Flight flight1;
    private Flight flight2;
    private List<Flight> flightList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();

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
    void createFlight() throws Exception {
        when(flightService.createFlight(any(Flight.class))).thenReturn(flight1);
        mockMvc.perform(post("/api/flight/post"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.origin").value("Madrid"))
        .andExpect(jsonPath("$.departuretime").value("08:15"))
        .andExpect(jsonPath("$.arrivaltime").value("10:20"))
        .andExpect(jsonPath("$.availableseats").value(60))
        .andExpect(jsonPath("$.status").value(true));
    }

}
