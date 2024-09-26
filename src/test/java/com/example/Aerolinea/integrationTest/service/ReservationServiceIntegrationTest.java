package com.example.Aerolinea.integrationTest.service;

import com.example.Aerolinea.model.Reservation;
import com.example.Aerolinea.repositories.IReservationRepository;
import com.example.Aerolinea.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceIntegrationTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private IReservationRepository iReservationRepository;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        iReservationRepository.deleteAll();
        reservation = new Reservation();
        reservation.setStatus(true);
        reservation.setReservationDate(LocalDateTime.now());
    }

    @Test
    void testCreateReservation() {
        Reservation savedReservation = reservationService.createReservation(reservation);

        assertNotNull(savedReservation);
        assertEquals(reservation.isStatus(), savedReservation.isStatus());
    }

    @Test
    void testGetReservationById() {
        Reservation savedReservation = iReservationRepository.save(reservation);

        Optional<Reservation> foundReservation = reservationService.getReservationById(savedReservation.getId());

        assertTrue(foundReservation.isPresent());
        assertEquals(savedReservation.getId(), foundReservation.get().getId());
    }

    @Test
    void testUpdateReservation() {
        Reservation savedReservation = iReservationRepository.save(reservation);
        savedReservation.setStatus(false);

        Reservation updatedReservation = reservationService.updateReservation(savedReservation.getId(), savedReservation);

        assertNotNull(updatedReservation);
        assertEquals(false, updatedReservation.isStatus());
    }

    @Test
    void testDeleteReservation() {
        Reservation savedReservation = iReservationRepository.save(reservation);

        reservationService.deleteReservation(savedReservation.getId());

        Optional<Reservation> deletedReservation = iReservationRepository.findById(savedReservation.getId());

        assertFalse(deletedReservation.isPresent());
    }
}
