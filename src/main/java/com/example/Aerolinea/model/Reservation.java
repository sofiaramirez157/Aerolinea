package com.example.Aerolinea.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = true)
    private User user;

    @Column(name = "reservationDate")
    LocalDateTime reservationDate;

    @Column(name = "status")
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "flight_id") // Foreign key in the Reservation table
    private Set<Flight> flights = new HashSet<>();}