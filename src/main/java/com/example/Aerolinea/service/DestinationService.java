package com.example.Aerolinea.service;

import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.repositories.IDestinationRepository;
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
        if (destination == null || destination.getName() == null || destination.getCountry() == null) {
            throw new InvalidRequestException("Invalid destination data provided.");
        }
        return iDestinationRepository.save(destination);
    }

    public List<Destination> getAllDestination() {
        try {
            return iDestinationRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving destinations.", e);
        }
    }

    public Destination getDestinationById(long id) {
        return iDestinationRepository.findById(id)
                .orElseThrow(() -> new DestinationNotFoundException("Destination not found with ID: " + id));
    }

    public void updateDestination(Destination destination, long id) {
        if (!iDestinationRepository.existsById(id)) {
            throw new DestinationNotFoundException("Destination not found with ID: " + id);
        }

        if (destination.getName() == null || destination.getCountry() == null) {
            throw new InvalidRequestException("Invalid destination data provided.");
        }

        destination.setId(id);
        iDestinationRepository.save(destination);
    }

    public boolean deleteDestination(long id) {
        if (!iDestinationRepository.existsById(id)) {
            throw new DestinationNotFoundException("Destination not found with ID: " + id);
        }

        try {
            iDestinationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting destination.", e);
        }
    }
}