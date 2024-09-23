package com.example.Aerolinea.service;

import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.repositories.IDestinationRepository;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {
    private IDestinationRepository iDestinationRepository;

    public Destination createDestination(Destination destination) {
        return iDestinationRepository.save(destination);
    }

    public List<Destination> getAllDestination() {
        try {
            return iDestinationRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving destination.", e);
        }
    }

    public Optional<Destination> getDestinationById(long id) {
        try {
            return iDestinationRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving destination details.", e);
        }
    }

    public void updateDestination(Destination destination, long id) {
        destination.setId(id);
        iDestinationRepository.save(destination);
    }

    public boolean deleteDestination(long id) {
        try {
            iDestinationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
