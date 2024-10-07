package com.example.Aerolinea.service;

import com.example.Aerolinea.model.Reservation;
import com.example.Aerolinea.repositories.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final IReservationRepository iReservationRepository;

    @Autowired
    public ReservationService(IReservationRepository iReservationRepository) {
        this.iReservationRepository = iReservationRepository;
    }

    public Reservation createReservation(Reservation reservation) {
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
                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + id));
    }

    public void deleteReservation(Long id) {
        if (iReservationRepository.existsById(id)) {
            iReservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reservation not found with id " + id);
        }
    }
}