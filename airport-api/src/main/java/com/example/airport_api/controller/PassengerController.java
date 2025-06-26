package com.example.airport_api.controller;

import com.example.airport_api.model.Passenger;
import com.example.airport_api.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    @Autowired
    private PassengerRepository passengerRepository;

    @GetMapping
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        return passengerRepository.findById(id)
                .map(passenger -> new ResponseEntity<>(passenger, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

     @GetMapping("/byAircraft/{aircraftId}")
    public List<Passenger> getPassengersByAircraft(@PathVariable Long aircraftId) {
        return passengerRepository.findByAircraftId(aircraftId);
    }

    @PostMapping
    public Passenger createPassenger(@RequestBody Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @RequestBody Passenger passenger) {
        return passengerRepository.findById(id)
                .map(existingPassenger -> {
                    passenger.setId(id); // Ensure the ID is set for updating
                    Passenger updatedPassenger = passengerRepository.save(passenger);
                    return new ResponseEntity<>(updatedPassenger, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        if (passengerRepository.existsById(id)) {
            passengerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}