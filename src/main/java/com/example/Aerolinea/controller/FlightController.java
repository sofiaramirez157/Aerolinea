
package com.example.Aerolinea.controller;

import com.example.Aerolinea.dto.FlightRequestDTO;
import com.example.Aerolinea.dto.FlightResponseDTO;
import com.example.Aerolinea.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<FlightResponseDTO> createFlight(@RequestBody FlightRequestDTO flightRequestDTO) {
        FlightResponseDTO createdFlight = flightService.createFlight(flightRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
    }

    @GetMapping
    public ResponseEntity<List<FlightResponseDTO>> getAllFlights() {
        List<FlightResponseDTO> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponseDTO> getFlightById(@PathVariable long id) {
        FlightResponseDTO flight = flightService.getFlightById(id);
        return ResponseEntity.ok(flight);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponseDTO> updateFlight(@RequestBody FlightRequestDTO flightRequestDTO, @PathVariable long id) {
        FlightResponseDTO updatedFlight = flightService.updateFlight(flightRequestDTO, id);
        return ResponseEntity.ok(updatedFlight);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlightById(@PathVariable long id) {
        flightService.deleteFlightById(id);
        return ResponseEntity.ok("Flight with ID " + id + " was deleted.");
    }
}