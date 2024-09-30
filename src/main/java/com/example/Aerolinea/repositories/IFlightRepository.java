package com.example.Aerolinea.repositories;

import com.example.Aerolinea.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFlightRepository extends JpaRepository<Flight, Long> {
}
