package com.example.Aerolinea.integrationTest.controller;


import com.example.Aerolinea.controller.FlightController;
import com.example.Aerolinea.model.Flight;
import com.example.Aerolinea.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String flightJson = objectMapper.writeValueAsString(flight1);

        mockMvc.perform(post("/api/flight/post")
            .contentType("application/json")
            .content(flightJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.origin").value("Madrid"))
            .andExpect(jsonPath("$.departureTime").value("08:15"))
            .andExpect(jsonPath("$.arrivalTime").value("10:20"))
            .andExpect(jsonPath("$.availableSeats").value(60))
            .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    void getAllFlight() throws Exception {
        when(flightService.getAllFlight()).thenReturn(flightList);
        mockMvc.perform(get("/api/flight/get"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].origin").value("Madrid"))
            .andExpect(jsonPath("$[0].departureTime").value("08:15"))
            .andExpect(jsonPath("$[0].arrivalTime").value("10:20"))
            .andExpect(jsonPath("$[0].availableSeats").value(60))
            .andExpect(jsonPath("$[0].status").value(true))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].origin").value("Scotland"))
            .andExpect(jsonPath("$[1].departureTime").value("15:00"))
            .andExpect(jsonPath("$[1].arrivalTime").value("20:35"))
            .andExpect(jsonPath("$[1].availableSeats").value(0))
            .andExpect(jsonPath("$[1].status").value(false));
    }

    @Test
    void getFlightById() throws Exception {
        when(flightService.getFlightById(2)).thenReturn(Optional.of(flight2));
        mockMvc.perform(get("/api/flight/get/2"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.origin").value("Scotland"))
        .andExpect(jsonPath("$.departureTime").value("15:00"))
        .andExpect(jsonPath("$.arrivalTime").value("20:35"))
        .andExpect(jsonPath("$.availableSeats").value(0))
        .andExpect(jsonPath("$.status").value(false));
    }

    @Test
    void updateFlight() throws Exception {  
        Flight updateFlight = new Flight();
        updateFlight.setId(1);
        updateFlight.setOrigin("Madrid");
        updateFlight.setDepartureTime(LocalTime.of(8, 15));
        updateFlight.setArrivalTime(LocalTime.of(11, 00));
        updateFlight.setAvailableSeats(35);
        updateFlight.setStatus(true);
    
        String updateFlightJson = "{\"id\":1,\n"
        + "\"origin\":\"Madrid\",\n"
        + "\"departureTime\":\"08:15\",\n"
        + "\"arrivalTime\":\"11:00\",\n"
        + "\"availableSeats\":35,\n"
        + "\"status\":true}";
    
        mockMvc.perform(put("/api/flight/put/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateFlightJson))
                .andExpect(status().isOk());
    
        verify(flightService).updateFlight(any(Flight.class), any(Long.class));
        }

        @Test
         void deleteFlightTest() throws Exception{
            when(flightService.deleteFlight(1)).thenReturn(true);

            mockMvc.perform(delete("/api/flight/delete/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value("Flight with id 1 was deleted"));

        }

}
