package com.example.Aerolinea.dto;

import com.example.Aerolinea.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReservationDTO {
    private Long id;
    private String username;
    //flightDetails: FlightDTO
    private String reservationDate;
    private String status;

    public static  ReservationDTO fromRerservation(Reservation reservation){
        return new ReservationDTO(
                reservation.getId(),
                reservation.getUsername(),
                reservation.getFlight(),
                reservation.getReservationDate(),
                reservation.getStatus(),
        );
    }
}
