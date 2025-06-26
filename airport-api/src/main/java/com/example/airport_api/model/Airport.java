package com.example.airport_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city; // Airport belongs to a city

     @ManyToMany(mappedBy = "airports")
    private List<Aircraft> aircrafts;

}