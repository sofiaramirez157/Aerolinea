package com.example.Aerolinea.integrationTest.controller;

import com.example.Aerolinea.model.Reservation;
import com.example.Aerolinea.repositories.IReservationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IReservationRepository reservationRepository;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        reservation = new Reservation();
        reservation.setStatus(true);
        reservation.setReservationDate(LocalDateTime.now());
    }

    @Test
    void testCreateReservation() throws Exception {
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    void testGetAllReservations() throws Exception {
        reservationRepository.save(reservation);

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value(true));
    }

    @Test
    void testGetReservationById() throws Exception {
        Reservation savedReservation = reservationRepository.save(reservation);

        mockMvc.perform(get("/api/reservations/" + savedReservation.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedReservation.getId()));
    }

    @Test
    void testUpdateReservation() throws Exception {
        Reservation savedReservation = reservationRepository.save(reservation);
        savedReservation.setStatus(false);

        mockMvc.perform(put("/api/reservations/" + savedReservation.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(savedReservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(false));
    }

    @Test
    void testDeleteReservation() throws Exception {
        Reservation savedReservation = reservationRepository.save(reservation);

        mockMvc.perform(delete("/api/reservations/" + savedReservation.getId()))
                .andExpect(status().isNoContent());
    }
}
