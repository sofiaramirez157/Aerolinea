package com.example.Aerolinea.integrationTest.controller;

import com.example.Aerolinea.controller.ReservationController;
import com.example.Aerolinea.model.Reservation;
import com.example.Aerolinea.model.User;
import com.example.Aerolinea.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservationControllerIntegrationTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    private MockMvc mockMvc;
    private Reservation reservation1;
    private Reservation reservation2;
    private List<Reservation> reservationList;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        reservation1 = new Reservation();
        reservation1.setId(1L);
        reservation1.setUser(user);
        reservation1.setReservationDate(LocalDateTime.now());
        reservation1.setFlight(flight);
    }

    @Test
    void createReservationTest() throws Exception {
        when(reservationService.createReservation(any(Reservation.class))).thenReturn(reservation1);

        ObjectMapper objectMapper = new ObjectMapper();
        String reservationJson = objectMapper.writeValueAsString(reservation1);

        mockMvc.perform(post("/api/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.user"))
    }
}
