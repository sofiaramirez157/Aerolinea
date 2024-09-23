package com.example.Aerolinea.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Destination")

public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;
}
