package com.example.Aerolinea.repository;

import com.example.Aerolinea.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFlightRepository extends JpaRepository<Flight, Long> {
}
