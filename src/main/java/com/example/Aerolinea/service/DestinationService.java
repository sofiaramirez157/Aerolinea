package com.example.Aerolinea.service;

import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.repository.IDestinationRepository;
import com.example.Aerolinea.exceptions.DestinationNotFoundException;
import com.example.Aerolinea.exceptions.InvalidRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {
    private final IDestinationRepository iDestinationRepository;

    public DestinationService(IDestinationRepository iDestinationRepository) {
        this.iDestinationRepository = iDestinationRepository;
    }

    public Destination createDestination(Destination destination) {
        if (destination == null || destination.getCountry() == null || destination.getCode() == null) {
            throw new InvalidRequestException("Invalid destination data provided.");
        }
        return iDestinationRepository.save(destination);
    }

    public List<Destination> getAllDestination() {
        return iDestinationRepository.findAll();
    }

    public Destination getDestinationById(long id) {
        return iDestinationRepository.findById(id)
                .orElseThrow(() -> new DestinationNotFoundException("Destination not found with ID: " + id));
    }

    public Destination updateDestination(Destination destination, long id) {
        if (!iDestinationRepository.existsById(id)) {
            throw new DestinationNotFoundException("Destination not found with ID: " + id);
        }

        Destination existingDestination = iDestinationRepository.findById(id)
                .orElseThrow(() -> new DestinationNotFoundException("Destination not found with ID: " + id));

        if (destination.getCountry() != null) {
            existingDestination.setCountry(destination.getCountry());
        }
        if (destination.getCode() != null) {
            existingDestination.setCode(destination.getCode());
        }

        return iDestinationRepository.save(existingDestination);
    }

    public boolean deleteDestination(long id) {
        if (!iDestinationRepository.existsById(id)) {
            throw new DestinationNotFoundException("Destination not found with ID: " + id);
        }

        iDestinationRepository.deleteById(id);
        return true;
    }
}