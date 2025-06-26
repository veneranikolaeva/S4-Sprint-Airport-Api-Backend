package com.example.airport_api.repository;

import com.example.airport_api.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Long> {

     @Query("SELECT a FROM Airport a WHERE a.city.id = :cityId")
    List<Airport> findByCityId(@Param("cityId") Long cityId);

}