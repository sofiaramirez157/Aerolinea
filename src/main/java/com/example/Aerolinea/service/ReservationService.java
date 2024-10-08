package com.example.Aerolinea.service;

import com.example.Aerolinea.model.Reservation;
import com.example.Aerolinea.repositories.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    IReservationRepository iReservationRepository;

    public Reservation createReservation(Reservation reservation) {
        return iReservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservation(){
            return iReservationRepository.findAll();
        }

    public Optional<Reservation> getReservationById(Long id) {
        return iReservationRepository.findById(id);
    }

    public Reservation updateReservation(Reservation reservation, long id){
        reservation.setId(id);
        return iReservationRepository.save(reservation);
    }

    public boolean deleteReservation(long id){
        try {
            iReservationRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
