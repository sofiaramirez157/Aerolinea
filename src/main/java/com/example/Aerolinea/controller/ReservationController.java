package com.example.Aerolinea.controller;

import com.example.Aerolinea.model.Reservation;
import com.example.Aerolinea.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @PostMapping
    public Reservation createReservation(Reservation reservation){
        return reservationService.createReservation(reservation);
    }

    @GetMapping
    public List<Reservation> getAllReservation(){
        return reservationService.getAllReservation();
    }

    @GetMapping("/{id}")
    public Optional<Reservation> getReservationById(@PathVariable long id){
        return reservationService.getReservationById(id);
    }

    @PutMapping("/{id}")
    public Reservation updeteReservation(@RequestBody Reservation reservation, @PathVariable long id){
        return reservationService.updateReservation(reservation, id);
    }

    @DeleteMapping("/{id}")
    public String deleteReservationById(@PathVariable long id){
        boolean ok = reservationService.deleteReservation(id);
        if (ok){
            return "Reservation with id" + id + "was deleted";
        }else {
            return "Reservation with id" + id + "not found";
        }
    }
}
