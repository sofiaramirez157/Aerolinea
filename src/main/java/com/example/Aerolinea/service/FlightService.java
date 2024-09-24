package com.example.Aerolinea.service;

import com.example.Aerolinea.repositories.IFlightRepository;
import com.example.Aerolinea.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    @Autowired IFlightRepository iFlightRepository;

    public Flight createFlight(Flight newFlight) {
        return iFlightRepository.save(newFlight);
    }

    public List<Flight> getAllFlight() {
        try {
            return iFlightRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving flights", e);
        }
    }

    public Optional<Flight> getFlightById(long id) {
        try {
            return iFlightRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving flight details", e);
        }
    }

    public void updateFlight(Flight flight, long id) {
        flight.setId(id);
        iFlightRepository.save(flight);
    }

    public boolean deleteFlight(long id) {
        try {
            iFlightRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
