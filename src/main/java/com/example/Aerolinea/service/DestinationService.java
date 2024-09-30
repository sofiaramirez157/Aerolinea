package com.example.Aerolinea.service;

import com.example.Aerolinea.exceptions.ResourceNotFoundException;
import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.repositories.IDestinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {
    private final IDestinationRepository destinationRepository;

    public DestinationService(IDestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public Destination createDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    public List<Destination> getAllDestination() {
        return destinationRepository.findAll();
    }

    public Optional<Destination> getDestinationById(long id) {
        return destinationRepository.findById(id);
    }

    public Optional<Destination> updateDestination(Destination destination, long id) {
        if (!destinationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Destination not found with id: " + id);
        }
        destination.setId(id);
        return Optional.of(destinationRepository.save(destination));
    }

    public boolean deleteDestination(long id) {
        if (!destinationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Destination not found with id: " + id);
        }
        destinationRepository.deleteById(id);
        return true;
    }
}