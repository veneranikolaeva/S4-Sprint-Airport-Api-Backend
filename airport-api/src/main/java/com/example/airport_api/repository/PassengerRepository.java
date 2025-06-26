package com.example.airport_api.repository;

import com.example.airport_api.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

     @Query("SELECT p FROM Passenger p JOIN p.aircrafts a WHERE a.id = :aircraftId")
    List<Passenger> findByAircraftId(@Param("aircraftId") Long aircraftId);
}

