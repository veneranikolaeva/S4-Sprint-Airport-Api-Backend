package com.example.airport_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String state;
    private Long population;

    @OneToMany(mappedBy = "city")
    private List<Passenger> passengers;

    @OneToMany(mappedBy = "city")
    private List<Airport> airports;

}