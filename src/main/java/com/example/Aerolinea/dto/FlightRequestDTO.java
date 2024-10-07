package com.example.Aerolinea.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequestDTO {
    private String origin;
    private long destinationId;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int availableSeats;
    private boolean status;
}