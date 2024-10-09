package com.example.Aerolinea.controller;

import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.service.DestinationService;
import com.example.Aerolinea.exceptions.DestinationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> deleteDestinationById(@PathVariable long id) {
        try {
            if (destinationService.deleteDestination(id)) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Destination with ID " + id + " was deleted.");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal Server Error"));
        } catch (DestinationNotFoundException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", ex.getMessage());
            response.put("timestamp", System.currentTimeMillis());

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }
    }
}