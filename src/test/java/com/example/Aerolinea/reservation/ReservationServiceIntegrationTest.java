package com.example.Aerolinea.reservation;

import com.example.Aerolinea.model.*;
import com.example.Aerolinea.repository.IReservationRepository;
import com.example.Aerolinea.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
        User user = new User();
        user.setId(1L);
        user.setUsername("usuario123");
        user.setEmail("user@example.com");
        user.setPassword("pepe");
        user.setRole(ERole.USER);

        Destination destination = new Destination();
        destination.setId(1L);
        destination.setCountry("España");
        destination.setCode("123456");

        Flight flight = new Flight();
        flight.setId(1L);
        flight.setOrigin("NYC");
        flight.setDestination(destination);
        flight.setDepartureTime(LocalTime.of(10, 0));
        flight.setArrivalTime(LocalTime.of(14, 0));
        flight.setAvailableSeats(150);
        flight.setStatus(true);

        reservation.setUser(user);
        reservation.getFlights().add(flight);

        when(iReservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation savedReservation = reservationService.createReservation(reservation);

        assertNotNull(savedReservation);
        assertEquals(reservation.getReservationDate(), savedReservation.getReservationDate());
        assertEquals(reservation.isStatus(), savedReservation.isStatus());
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
