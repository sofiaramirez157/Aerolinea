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

        if (destination.getName() == null || destination.getCountry() == null) {
            throw new InvalidRequestException("Invalid destination data provided.");
        }

        // Set the ID of the updated destination
        destination.setId(id);

        return iDestinationRepository.save(destination); // Return the updated destination
    }

    public boolean deleteDestination(long id) {
        if (!iDestinationRepository.existsById(id)) {
            throw new DestinationNotFoundException("Destination not found with ID: " + id);
        }

        iDestinationRepository.deleteById(id);
        return true;
    }
}
