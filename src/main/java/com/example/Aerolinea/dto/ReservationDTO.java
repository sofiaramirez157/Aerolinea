package com.example.Aerolinea.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long id;
    private Long userId; // Assuming you want to include only the user ID
    private LocalDateTime reservationDate;
    private boolean status;
    private Set<Long> flightIds = new HashSet<>(); // Assuming you want to transfer flight IDs
}
