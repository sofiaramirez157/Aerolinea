package com.example.Aerolinea.integrationTest.controller;

import com.example.Aerolinea.controller.ReservationController;
import com.example.Aerolinea.model.Reservation;
import com.example.Aerolinea.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @Autowired
    private ObjectMapper objectMapper;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservation = new Reservation();
        reservation.setStatus(true);
        reservation.setReservationDate(LocalDateTime.now());
    }

    @Test
    void testCreateReservation() throws Exception {
        when(reservationService.createReservation(any(Reservation.class))).thenReturn(reservation);

        String reservationJson = objectMapper.writeValueAsString(reservation);

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true));

        verify(reservationService, times(1)).createReservation(any(Reservation.class));
    }

    @Test
    void testGetAllReservations() throws Exception {
        when(reservationService.getAllReservations()).thenReturn(List.of(reservation));

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value(true));

        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    void testGetReservationById() throws Exception {
        when(reservationService.getReservationById(anyLong())).thenReturn(Optional.of(reservation));

        mockMvc.perform(get("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservation.getId()));

        verify(reservationService, times(1)).getReservationById(1L);
    }

    @Test
    void testUpdateReservation() throws Exception {
        reservation.setStatus(false);

        when(reservationService.updateReservation(anyLong(), any(Reservation.class))).thenReturn(reservation);

        String updatedReservationJson = objectMapper.writeValueAsString(reservation);

        mockMvc.perform(put("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedReservationJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(false));

        verify(reservationService, times(1)).updateReservation(anyLong(), any(Reservation.class));
    }

    @Test
    void testDeleteReservation() throws Exception {
        doNothing().when(reservationService).deleteReservation(anyLong());

        mockMvc.perform(delete("/api/reservations/1"))
                .andExpect(status().isNoContent());

        verify(reservationService, times(1)).deleteReservation(1L);
    }
}
