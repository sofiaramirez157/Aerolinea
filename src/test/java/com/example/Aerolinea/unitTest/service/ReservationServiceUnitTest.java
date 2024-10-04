package com.example.Aerolinea.unitTest.service;

import com.example.Aerolinea.model.Reservation;
import com.example.Aerolinea.repositories.IReservationRepository;
import com.example.Aerolinea.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ReservationServiceUnitTest {

    @Mock
    private IReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

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
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation savedReservation = reservationService.createReservation(reservation);

        assertNotNull(savedReservation);
        assertEquals(reservation.getId(), savedReservation.getId());
    }

    @Test
    void testGetReservationById() {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));

        Optional<Reservation> foundReservation = reservationService.getReservationById(1L);

        assertTrue(foundReservation.isPresent());
        assertEquals(reservation.getId(), foundReservation.get().getId());
    }

    @Test
    void testUpdateReservation() {
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation updatedReservation = reservationService.updateReservation(1L, reservation);

        assertNotNull(updatedReservation);
        assertEquals(reservation.getId(), updatedReservation.getId());
    }

    @Test
    void testDeleteReservation() {
        when(reservationRepository.existsById(1L)).thenReturn(true);

        reservationService.deleteReservation(1L);

        verify(reservationRepository, times(1)).deleteById(1L);
    }
}
