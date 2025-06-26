package com.example.airport_api.controller;

import com.example.airport_api.model.Aircraft;
import com.example.airport_api.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
public class AircraftController {

    @Autowired
    private AircraftRepository aircraftRepository;

    @GetMapping
    public List<Aircraft> getAllAircraft() {
        return aircraftRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aircraft> getAircraftById(@PathVariable Long id) {
        return aircraftRepository.findById(id)
                .map(aircraft -> new ResponseEntity<>(aircraft, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

     @GetMapping("/byPassenger/{passengerId}")
    public List<Aircraft> getAircraftByPassenger(@PathVariable Long passengerId) {
        return aircraftRepository.findAircraftByPassengerId(passengerId);
    }

    @GetMapping("/byAirport/{airportId}")
    public List<Aircraft> getAircraftByAirport(@PathVariable Long airportId) {
        return aircraftRepository.findAircraftByAirportId(airportId);
    }

    @PostMapping
    public Aircraft createAircraft(@RequestBody Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aircraft> updateAircraft(@PathVariable Long id, @RequestBody Aircraft aircraft) {
        return aircraftRepository.findById(id)
                .map(existingAircraft -> {
                    aircraft.setId(id); 
                    Aircraft updatedAircraft = aircraftRepository.save(aircraft);
                    return new ResponseEntity<>(updatedAircraft, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable Long id) {
        if (aircraftRepository.existsById(id)) {
            aircraftRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}