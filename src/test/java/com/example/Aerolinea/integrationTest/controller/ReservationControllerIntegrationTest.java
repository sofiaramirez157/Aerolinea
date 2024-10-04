package com.example.Aerolinea.integrationTest.controller;

import com.example.Aerolinea.model.Reservation;
import com.example.Aerolinea.testConfig.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Reservation testReservation;

    @BeforeEach
    void setUp() {
        testReservation = new Reservation();
        testReservation.setReservationDate(LocalDateTime.now());
        testReservation.setStatus(true);
        testReservation.setUser(null);  // Set User to null since we don't have the User entity yet
    }

    @Test
    void createReservationTest() throws Exception {
        String reservationJson = objectMapper.writeValueAsString(testReservation);

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber()) // Check if ID is generated
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    void getAllReservationsTest() throws Exception {
        mockMvc.perform(get("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").isNumber());  // Ensure some reservations exist
    }

    @Test
    void getReservationByIdTest() throws Exception {
        String reservationJson = objectMapper.writeValueAsString(testReservation);
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/reservations/1")  // Assuming the ID is 1
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateReservationTest() throws Exception {
        String reservationJson = objectMapper.writeValueAsString(testReservation);

        MvcResult result = mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Reservation createdReservation = objectMapper.readValue(responseBody, Reservation.class);
        Long reservationId = createdReservation.getId(); // Get the actual ID of the created reservation

        mockMvc.perform(get("/api/reservations/" + reservationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservationId)); // Ensure it returns the correct ID

        testReservation.setStatus(false);
        String updatedReservationJson = objectMapper.writeValueAsString(testReservation);

        mockMvc.perform(put("/api/reservations/" + reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedReservationJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(false));
    }




    @Test
    void deleteReservationTest() throws Exception {
        String reservationJson = objectMapper.writeValueAsString(testReservation);
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
