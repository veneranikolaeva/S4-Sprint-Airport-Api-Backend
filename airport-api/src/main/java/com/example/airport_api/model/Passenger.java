package com.example.airport_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;

     @NotNull
    private String lastName;
    private String phoneNumber;
    
       @ManyToOne
    @JoinColumn(name = "city_id")
    private City city; //Passenger lives in a city

     @ManyToMany
    @JoinTable(
            name = "passenger_aircraft",
            joinColumns = @JoinColumn(name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "aircraft_id")
    )
    private List<Aircraft> aircrafts;

}