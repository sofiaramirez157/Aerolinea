package com.example.Aerolinea.controller;

import com.example.Aerolinea.exceptions.DestinationNotFoundException;
import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.service.DestinationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/")
    public ResponseEntity<Destination> createDestination(@RequestBody Destination destination) {
        Destination createdDestination = destinationService.createDestination(destination);
        return new ResponseEntity<>(createdDestination, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Destination>> getAllDestination() {
        List<Destination> destinations = destinationService.getAllDestination();
        return new ResponseEntity<>(destinations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable long id) {
        Destination destination = destinationService.getDestinationById(id);
        return new ResponseEntity<>(destination, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDestination(@RequestBody Destination destination, @PathVariable long id) {
        destinationService.updateDestination(destination, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDestinationById(@PathVariable long id) {
        if (destinationService.deleteDestination(id)) {
            return new ResponseEntity<>("Destination with ID " + id + " was deleted.", HttpStatus.OK);
        } else {
            throw new DestinationNotFoundException("Destination not found with ID: " + id);
        }
    }
}