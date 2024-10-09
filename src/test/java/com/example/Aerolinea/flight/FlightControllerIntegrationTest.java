
package com.example.Aerolinea.flight;

import com.example.Aerolinea.controller.FlightController;
import com.example.Aerolinea.dto.FlightRequestDTO;
import com.example.Aerolinea.dto.FlightResponseDTO;
import com.example.Aerolinea.model.Flight;
import com.example.Aerolinea.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FlightControllerIntegrationTest {

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

    private MockMvc mockMvc;

    private Flight flight1;
    private Flight flight2;
    private List<FlightResponseDTO> flightResponseList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();

        flight1 = createFlight(1, "Madrid", LocalTime.of(8, 15), LocalTime.of(10, 20), 60, true);
        flight2 = createFlight(2, "Scotland", LocalTime.of(15, 0), LocalTime.of(20, 35), 0, false);

        flightResponseList.add(convertToResponseDTO(flight1));
        flightResponseList.add(convertToResponseDTO(flight2));
    }

    private Flight createFlight(int id, String origin, LocalTime departureTime, LocalTime arrivalTime, int availableSeats, boolean status) {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setOrigin(origin);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        flight.setAvailableSeats(availableSeats);
        flight.setStatus(status);
        return flight;
    }

    private FlightResponseDTO convertToResponseDTO(Flight flight) {
        return new FlightResponseDTO(
                flight.getId(),
                flight.getOrigin(),
                null,
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getAvailableSeats(),
                flight.isStatus()
        );
    }

    @Test
    public void testCreateFlight() throws Exception {
        FlightRequestDTO flightRequest = new FlightRequestDTO();
        flightRequest.setOrigin("Madrid");
        flightRequest.setDestinationId(1);
        flightRequest.setDepartureTime(LocalTime.of(8, 15));
        flightRequest.setArrivalTime(LocalTime.of(10, 20));
        flightRequest.setAvailableSeats(60);
        flightRequest.setStatus(true);

        FlightResponseDTO createdFlight = convertToResponseDTO(createFlight(1, "Madrid", LocalTime.of(8, 15), LocalTime.of(10, 20), 60, true));

        when(flightService.createFlight(any(FlightRequestDTO.class))).thenReturn(createdFlight);

        mockMvc.perform(post("/api/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(flightRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.origin").value("Madrid"));
    }

    @Test
    public void testGetAllFlights() throws Exception {
        when(flightService.getAllFlights()).thenReturn(flightResponseList);

        mockMvc.perform(get("/api/flights"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(flightResponseList.size()));
    }

    @Test
    public void testGetFlightById() throws Exception {
        when(flightService.getFlightById(eq(1L))).thenReturn(flightResponseList.get(0));

        mockMvc.perform(get("/api/flights/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.origin").value("Madrid"));
    }

    @Test
    public void testUpdateFlight() throws Exception {
        FlightRequestDTO flightRequest = new FlightRequestDTO();
        flightRequest.setOrigin("Updated Origin");
        flightRequest.setDestinationId(1);
        flightRequest.setDepartureTime(LocalTime.of(9, 0));
        flightRequest.setArrivalTime(LocalTime.of(11, 0));
        flightRequest.setAvailableSeats(50);
        flightRequest.setStatus(true);

        FlightResponseDTO updatedFlight = convertToResponseDTO(createFlight(1, "Updated Origin", LocalTime.of(9, 0), LocalTime.of(11, 0), 50, true));

        when(flightService.updateFlight(any(FlightRequestDTO.class), eq(1L))).thenReturn(updatedFlight);

        mockMvc.perform(put("/api/flights/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(flightRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.origin").value("Updated Origin"));
    }

    @Test
    public void testDeleteFlight() throws Exception {
        doNothing().when(flightService).deleteFlightById(1L);

        mockMvc.perform(delete("/api/flights/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Flight with ID 1 was deleted."));
    }
}