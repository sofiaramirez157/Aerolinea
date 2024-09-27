package com.example.Aerolinea.controller;

import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.service.DestinationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/destination")
@CrossOrigin

public class DestinationController {
    DestinationService destinationService;

    @PostMapping(path = "/")
    public Destination createDestination(@RequestBody Destination destination){
        return destinationService.createDestination(destination);
    }

    @GetMapping(path = "/")
    public List<Destination> getAllDestination(){
        return destinationService.getAllDestination();
    }

    @GetMapping(path = "/{id}")
    public Optional<Destination> getDestinationId(@PathVariable long id){
        return destinationService.getDestinationById(id);
    }

    @PutMapping(path = "/{id}")
    public Destination updateDestination(@RequestBody Destination destination, @PathVariable long id){
        return destinationService.updateDestination(destination, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteDestinationById(@PathVariable long id){
        boolean ok = destinationService.deleteDestination(id);
        if (ok){
            return "Destination with id " + id + " was delete";
        } else {
            return "Destination with id " + id + " not found";
        }
    }
}
