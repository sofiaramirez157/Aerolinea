package com.example.Aerolinea.service;

import com.example.Aerolinea.model.Flight;
import com.example.Aerolinea.repositories.IFlightRepository;
import com.example.Aerolinea.exceptions.FlightNotAvailableException;
import com.example.Aerolinea.exceptions.ResourceNotFoundException;
import com.example.Aerolinea.exceptions.InvalidRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    private final IFlightRepository iFlightRepository;

    public FlightService(IFlightRepository iFlightRepository) {
        this.iFlightRepository = iFlightRepository;
    }

    public Flight createFlight(Flight newFlight) {
        validateFlight(newFlight);
        return iFlightRepository.save(newFlight);
    }

    public List<Flight> getAllFlight() {
        return iFlightRepository.findAll();
    }

    public Flight getFlightById(long id) {
        return iFlightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with ID: " + id));
    }

    public void updateFlight(Flight flight, long id) {
        Flight existingFlight = getFlightById(id);
        validateFlight(flight);
        flight.setId(id);
        iFlightRepository.save(flight);
    }

    public void deleteFlight(long id) {
        if (!iFlightRepository.existsById(id)) {
            throw new ResourceNotFoundException("Flight not found with ID: " + id);
        }
        iFlightRepository.deleteById(id);
    }

    public void checkFlightAvailability(Flight flight) {
        if (flight.getAvailableSeats() <= 0) {
            throw new FlightNotAvailableException("Flight with ID " + flight.getId() + " has no available seats.");
        }
    }

    private void validateFlight(Flight flight) {
        if (flight == null || flight.getOrigin() == null || flight.getDestination() == null) {
            throw new InvalidRequestException("Invalid flight data provided.");
        }
    }
}