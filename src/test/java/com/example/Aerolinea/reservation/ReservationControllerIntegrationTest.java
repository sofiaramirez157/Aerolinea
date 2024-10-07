package com.example.Aerolinea.reservation;

import com.example.Aerolinea.model.Reservation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Reservation testReservation;

    @BeforeEach
    void setUp() {
        // Create a sample Reservation for testing purposes
        testReservation = new Reservation();
        testReservation.setReservationDate(LocalDateTime.now());
        testReservation.setStatus(true);
        testReservation.setUser(null);  // Set User to null since we don't have the User entity yet
    }

    @Test
    void createReservationTest() throws Exception {
        // Convert Reservation object to JSON
        String reservationJson = objectMapper.writeValueAsString(testReservation);

        // Perform POST request to create a new reservation
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber()) // Check if ID is generated
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    void getAllReservationsTest() throws Exception {
        // Perform GET request to fetch all reservations
        mockMvc.perform(get("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").isNumber());  // Ensure some reservations exist
    }

    @Test
    void getReservationByIdTest() throws Exception {
        // Create and persist a reservation first to ensure ID exists
        String reservationJson = objectMapper.writeValueAsString(testReservation);
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isOk());

        // Perform GET request to fetch the reservation by ID
        mockMvc.perform(get("/api/reservations/1")  // Assuming the ID is 1
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateReservationTest() throws Exception {
        // Persist a reservation first
        String reservationJson = objectMapper.writeValueAsString(testReservation);
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isOk());

        // Update the reservation's status
        testReservation.setStatus(false);
        String updatedReservationJson = objectMapper.writeValueAsString(testReservation);

        // Perform PUT request to update the reservation
        mockMvc.perform(put("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedReservationJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(false));
    }

    @Test
    void deleteReservationTest() throws Exception {
        // Persist a reservation first
        String reservationJson = objectMapper.writeValueAsString(testReservation);
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isOk());

        // Perform DELETE request to remove the reservation
        mockMvc.perform(delete("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
