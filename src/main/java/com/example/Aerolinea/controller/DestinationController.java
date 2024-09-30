package com.example.Aerolinea.controller;

import com.example.Aerolinea.exceptions.ResourceNotFoundException;
import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.service.DestinationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destination")
@CrossOrigin
public class DestinationController {
    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @PostMapping(path = "/")
    public Destination createDestination(@RequestBody Destination destination) {
        return destinationService.createDestination(destination);
    }

    @GetMapping(path = "/")
    public List<Destination> getAllDestination() {
        return destinationService.getAllDestination();
    }

    @GetMapping(path = "/{id}")
    public Destination getDestinationById(@PathVariable long id) {
        return destinationService.getDestinationById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + id));
    }

    @PutMapping(path = "/{id}")
    public Destination updateDestination(@RequestBody Destination destination, @PathVariable long id) {
        return destinationService.updateDestination(destination, id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + id));
    }

    @DeleteMapping(path = "/{id}")
    public String deleteDestinationById(@PathVariable long id) {
        boolean ok = destinationService.deleteDestination(id);
        if (ok) {
            return "Destination with id " + id + " was deleted.";
        } else {
            throw new ResourceNotFoundException("Destination with id " + id + " not found.");
        }
    }
}