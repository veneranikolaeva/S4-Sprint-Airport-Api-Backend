package com.example.airport_api.controller;

import com.example.airport_api.model.Airport;
import com.example.airport_api.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    @Autowired
    private AirportRepository airportRepository;

    @GetMapping
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
        return airportRepository.findById(id)
                .map(airport -> new ResponseEntity<>(airport, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

     @GetMapping("/byCity/{cityId}")
    public List<Airport> getAirportsByCity(@PathVariable Long cityId) {
        return airportRepository.findByCityId(cityId);
    }

    @PostMapping
    public Airport createAirport(@RequestBody Airport airport) {
        return airportRepository.save(airport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long id, @RequestBody Airport airport) {
        return airportRepository.findById(id)
                .map(existingAirport -> {
                    airport.setId(id); // Ensure the ID is set for updating
                    Airport updatedAirport = airportRepository.save(airport);
                    return new ResponseEntity<>(updatedAirport, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        if (airportRepository.existsById(id)) {
            airportRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}