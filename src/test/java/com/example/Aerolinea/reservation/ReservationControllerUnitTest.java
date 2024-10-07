package com.example.Aerolinea.reservation;

import com.example.Aerolinea.controller.ReservationController;
import com.example.Aerolinea.model.Reservation;
import com.example.Aerolinea.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ReservationControllerUnitTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setStatus(true);
        reservation.setReservationDate(LocalDateTime.now());
    }

    @Test
    void testCreateReservation() {
        when(reservationService.createReservation(any(Reservation.class))).thenReturn(reservation);

        ResponseEntity<Reservation> response = reservationController.createReservation(reservation);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(reservation.getId(), response.getBody().getId());
    }

    @Test
    void testGetAllReservations() {
        when(reservationService.getAllReservations()).thenReturn(Collections.singletonList(reservation));

        ResponseEntity<?> response = reservationController.getAllReservations();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, ((List<?>) Objects.requireNonNull(response.getBody())).size());
    }

    @Test
    void testGetReservationById() {
        when(reservationService.getReservationById(anyLong())).thenReturn(Optional.of(reservation));

        ResponseEntity<Reservation> response = reservationController.getReservationById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(reservation.getId(), response.getBody().getId());
    }

    @Test
    void testUpdateReservation() {
        when(reservationService.updateReservation(anyLong(), any(Reservation.class))).thenReturn(reservation);

        ResponseEntity<Reservation> response = reservationController.updateReservation(1L, reservation);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(reservation.getId(), response.getBody().getId());
    }

    @Test
    void testDeleteReservation() {
        ResponseEntity<Void> response = reservationController.deleteReservation(1L);

        assertEquals(204, response.getStatusCodeValue());
    }
}
