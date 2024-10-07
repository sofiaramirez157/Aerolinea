package com.example.Aerolinea.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class FlightResponseDTO {
    private long id;
    private String origin;
    private DestinationDTO destination;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int availableSeats;
    private boolean status;
}