
package com.example.Aerolinea.service;

import com.example.Aerolinea.exceptions.InvalidRequestException;
import com.example.Aerolinea.exceptions.ReservationNotFoundException;
import com.example.Aerolinea.model.Reservation;
import com.example.Aerolinea.repository.IReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final IReservationRepository iReservationRepository;

    public ReservationService(IReservationRepository iReservationRepository) {
        this.iReservationRepository = iReservationRepository;
    }

    public Reservation createReservation(Reservation reservation) {
        if (reservation == null || reservation.getUser() == null || reservation.getFlights().isEmpty()) {
            throw new InvalidRequestException("Invalid reservation data provided.");
        }
        return iReservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return iReservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return iReservationRepository.findById(id);
    }

    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        return iReservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setUser(updatedReservation.getUser());
                    reservation.setReservationDate(updatedReservation.getReservationDate());
                    reservation.setStatus(updatedReservation.isStatus());
                    reservation.setFlights(updatedReservation.getFlights());
                    return iReservationRepository.save(reservation);
                })
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with ID: " + id));
    }

    public void deleteReservation(Long id) {
        if (!iReservationRepository.existsById(id)) {
            throw new ReservationNotFoundException("Reservation not found with ID: " + id);
        }
        iReservationRepository.deleteById(id);
    }
}