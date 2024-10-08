package com.example.Aerolinea.service;

import com.example.Aerolinea.dto.FlightRequestDTO;
import com.example.Aerolinea.dto.FlightResponseDTO;
import com.example.Aerolinea.dto.DestinationDTO;
import com.example.Aerolinea.exceptions.ResourceNotFoundException;
import com.example.Aerolinea.exceptions.FlightNotAvailableException;
import com.example.Aerolinea.exceptions.InvalidRequestException;
import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.model.Flight;
import com.example.Aerolinea.repositories.IDestinationRepository;
import com.example.Aerolinea.repositories.IFlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final IFlightRepository flightRepository;
    private final IDestinationRepository destinationRepository;

    public FlightService(IFlightRepository flightRepository, IDestinationRepository destinationRepository) {
        this.flightRepository = flightRepository;
        this.destinationRepository = destinationRepository;
    }

    public FlightResponseDTO createFlight(FlightRequestDTO flightRequestDTO) {
        validateFlightRequest(flightRequestDTO);

        Destination destination = destinationRepository.findById(flightRequestDTO.getDestinationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with ID: " + flightRequestDTO.getDestinationId()));

        Flight flight = new Flight();
        flight.setOrigin(flightRequestDTO.getOrigin());
        flight.setDestination(destination);
        flight.setDepartureTime(flightRequestDTO.getDepartureTime());
        flight.setArrivalTime(flightRequestDTO.getArrivalTime());
        flight.setAvailableSeats(flightRequestDTO.getAvailableSeats());
        flight.setStatus(flightRequestDTO.isStatus());

        Flight savedFlight = flightRepository.save(flight);
        return convertToResponseDTO(savedFlight);
    }

    public FlightResponseDTO updateFlight(FlightRequestDTO flightRequestDTO, long id) {
        validateFlightRequest(flightRequestDTO);

        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with ID: " + id));

        if (!flight.isStatus() || flight.getAvailableSeats() <= 0) {
            throw new FlightNotAvailableException("Flight is not available for update.");
        }

        Destination destination = destinationRepository.findById(flightRequestDTO.getDestinationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with ID: " + flightRequestDTO.getDestinationId()));

        flight.setOrigin(flightRequestDTO.getOrigin());
        flight.setDestination(destination);
        flight.setDepartureTime(flightRequestDTO.getDepartureTime());
        flight.setArrivalTime(flightRequestDTO.getArrivalTime());
        flight.setAvailableSeats(flightRequestDTO.getAvailableSeats());
        flight.setStatus(flightRequestDTO.isStatus());

        Flight updatedFlight = flightRepository.save(flight);
        return convertToResponseDTO(updatedFlight);
    }

    public FlightResponseDTO getFlightById(long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with ID: " + id));
        return convertToResponseDTO(flight);
    }

    public List<FlightResponseDTO> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteFlightById(long id) {
        if (!flightRepository.existsById(id)) {
            throw new ResourceNotFoundException("Flight not found with ID: " + id);
        }
        flightRepository.deleteById(id);
    }

    private FlightResponseDTO convertToResponseDTO(Flight flight) {
        DestinationDTO destinationDTO = new DestinationDTO(
                flight.getDestination().getId(),
                flight.getDestination().getCountry(),
                flight.getDestination().getCode()
        );

        return new FlightResponseDTO(
                flight.getId(),
                flight.getOrigin(),
                destinationDTO,
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getAvailableSeats(),
                flight.isStatus()
        );
    }

    private void validateFlightRequest(FlightRequestDTO flightRequestDTO) {
        if (flightRequestDTO == null || flightRequestDTO.getOrigin() == null || flightRequestDTO.getOrigin().isEmpty()) {
            throw new InvalidRequestException("Flight origin must not be empty.");
        }
        if (flightRequestDTO.getDestinationId() <= 0) {
            throw new InvalidRequestException("Invalid destination ID.");
        }
        if (flightRequestDTO.getAvailableSeats() < 0) {
            throw new InvalidRequestException("Available seats must be a non-negative value.");
        }
    }
}