package com.example.Aerolinea.service;

import com.example.Aerolinea.model.Reservation;
import com.example.Aerolinea.repositories.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final IReservationRepository reservationRepository;

    @Autowired
    public ReservationService(IReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setUser(updatedReservation.getUser());
                    reservation.setReservationDate(updatedReservation.getReservationDate());
                    reservation.setStatus(updatedReservation.isStatus());
                    reservation.setFlights(updatedReservation.getFlights());
                    return reservationRepository.save(reservation);
                })
                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + id));
    }

    public void deleteReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reservation not found with id " + id);
        }
    }
}
