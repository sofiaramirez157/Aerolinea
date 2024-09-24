package com.example.Aerolinea.controller;

import com.example.Aerolinea.model.Flight;
import com.example.Aerolinea.service.FlightService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flight")
@CrossOrigin

public class FlightController {
    @Autowired
    FlightService flightService;

    @PostMapping(path = "post")
    public Flight createFlight(@RequestBody Flight flight) {
        return flightService.createFlight(flight);
    }

    @GetMapping(path = "get")
    public List<Flight> getAllFlight() {
        return flightService.getAllFlight();
    }

    @GetMapping(path = "get/{id}")
    public Optional<Flight> getFlightById(@PathVariable long id) {
        return flightService.getFlightById(id);
    }

    @PutMapping(path = "put/{id}")
    public void updateFlight(@RequestBody Flight flight, @PathVariable long id) {
        flightService.updateFlight(flight, id);
    }

    @DeleteMapping(path = "delete/{id}")
    public String deleteFlightById(@PathVariable long id) {
        boolean ok = flightService.deleteFlight(id);
        if (ok) {
            return "Flight with id" + id + "was deleted";
        } else {
            return "Destination with id" + id + "not found";
        }
    }
}
