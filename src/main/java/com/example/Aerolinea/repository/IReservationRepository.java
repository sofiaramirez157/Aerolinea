package com.example.Aerolinea.repository;

import com.example.Aerolinea.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
}
