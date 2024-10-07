package com.example.Aerolinea.reservation;

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
import static org.mockito.Mockito.*;

class ReservationServiceIntegrationTest {

    @Mock
    private IReservationRepository iReservationRepository;

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
        when(iReservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation savedReservation = reservationService.createReservation(reservation);

        assertNotNull(savedReservation);
        assertEquals(reservation.isStatus(), savedReservation.isStatus());

        verify(iReservationRepository, times(1)).save(reservation);
    }

    @Test
    void testGetReservationById() {
        when(iReservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));

        Optional<Reservation> foundReservation = reservationService.getReservationById(1L);

        assertTrue(foundReservation.isPresent());
        assertEquals(reservation.getId(), foundReservation.get().getId());

        verify(iReservationRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateReservation() {
        when(iReservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));
        when(iReservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation updatedReservation = reservationService.updateReservation(1L, reservation);

        assertNotNull(updatedReservation);
        assertEquals(reservation.isStatus(), updatedReservation.isStatus());

        verify(iReservationRepository, times(1)).findById(1L);
        verify(iReservationRepository, times(1)).save(reservation);
    }

    @Test
    void testDeleteReservation() {
        when(iReservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(iReservationRepository.existsById(1L)).thenReturn(true);

        reservationService.deleteReservation(1L);

        verify(iReservationRepository, times(1)).deleteById(1L);
    }
}
