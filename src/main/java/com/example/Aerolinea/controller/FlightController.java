package com.example.Aerolinea.controller;

import com.example.Aerolinea.model.Flight;
import com.example.Aerolinea.service.FlightService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight")
@CrossOrigin
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/")
    public Flight createFlight(@RequestBody Flight flight) {
        return flightService.createFlight(flight);
    }

    @GetMapping("/")
    public List<Flight> getAllFlight() {
        return flightService.getAllFlight();
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable long id) {
        return flightService.getFlightById(id);
    }

    @PutMapping("/{id}")
    public void updateFlight(@RequestBody Flight flight, @PathVariable long id) {
        flightService.updateFlight(flight, id);
    }

    @DeleteMapping("/{id}")
    public String deleteFlightById(@PathVariable long id) {
        flightService.deleteFlight(id);
        return "Flight with ID " + id + " was deleted.";
    }
}