package com.example.airport_api.repository;

import com.example.airport_api.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

     @Query("SELECT DISTINCT a FROM Aircraft a JOIN a.passengers p WHERE p.id = :passengerId")
    List<Aircraft> findAircraftByPassengerId(@Param("passengerId") Long passengerId);

    @Query("SELECT DISTINCT a FROM Aircraft a JOIN a.airports ap WHERE ap.id = :airportId")
    List<Aircraft> findAircraftByAirportId(@Param("airportId") Long airportId);
}
