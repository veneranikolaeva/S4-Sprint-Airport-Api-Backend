package com.example.airport_api;

import com.example.airport_api.model.Aircraft;
import com.example.airport_api.model.Airport;
import com.example.airport_api.model.City;
import com.example.airport_api.model.Passenger;
import com.example.airport_api.repository.AircraftRepository;
import com.example.airport_api.repository.AirportRepository;
import com.example.airport_api.repository.CityRepository;
import com.example.airport_api.repository.PassengerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private PassengerRepository passengerRepository;

    private final Random random = new Random();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Seed Cities
        List<City> cities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            City city = new City();
            city.setName(generateCityName());
            city.setState(generateState());
            city.setPopulation(generatePopulation());
            cityRepository.save(city);
            cities.add(city);
        }

        // Seed Airports
        List<Airport> airports = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Airport airport = new Airport();
            airport.setName(generateAirportName());
            airport.setCode(generateAirportCode());
            airport.setCity(getRandomElement(cities));
            airportRepository.save(airport);
            airports.add(airport);
        }

        // Seed Aircraft
        List<Aircraft> aircrafts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Aircraft aircraft = new Aircraft();
            aircraft.setType(generateAircraftType());
            aircraft.setAirlineName(generateAirlineName());
            aircraft.setNumberOfPassengers(generateNumberOfPassengers());
            aircraftRepository.save(aircraft);
            aircrafts.add(aircraft);
        }

        // Seed Passengers
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Passenger passenger = new Passenger();
            passenger.setFirstName(generateFirstName());
            passenger.setLastName(generateLastName());
            passenger.setPhoneNumber(generatePhoneNumber());
            passenger.setCity(getRandomElement(cities));
            passenger.setAircrafts(new ArrayList<>()); // Initialize the list

            // Establish Relationships between Passengers and Aircrafts
            int numAircraft = random.nextInt(3) + 1; // Each passenger flies on 1-3 aircraft
            List<Aircraft> passengerAircraft = new ArrayList<>();
            for (int j = 0; j < numAircraft; j++) {
                Aircraft aircraft = getRandomElement(aircrafts);
                if (!passengerAircraft.contains(aircraft)) {
                    passengerAircraft.add(aircraft);
                }
            }

            passenger.setAircrafts(passengerAircraft);
            passengerRepository.save(passenger);
            passengers.add(passenger);

            // Establish Relationships between Aircraft and Airports
            for (Aircraft aircraft12 : aircrafts) {
                List<Airport> usedAirports = getRandomElements(airports, random.nextInt(airports.size()) + 1);
                aircraft12.setAirports(usedAirports);
                aircraftRepository.save(aircraft12);
            }
        }

        System.out.println("Database seeded with 10s of records and relationships established!");
    }

    private String generateCityName() {
        String[] cityNames = { "New York", "London", "Paris", "Tokyo", "Sydney", "Rome", "Berlin", "Madrid", "Toronto",
                "Chicago" };
        return cityNames[random.nextInt(cityNames.length)];
    }

    private String generateState() {
        String[] states = { "NY", "England", "Île-de-France", "Tokyo", "NSW", "Lazio", "Berlin", "Madrid", "ON", "IL" };
        return states[random.nextInt(states.length)];
    }

    private Long generatePopulation() {
        return (long) (random.nextInt(10000000) + 1000000); // Population between 1M and 11M
    }

    private String generateAirportName() {
        return generateCityName() + " International Airport";
    }

    private String generateAirportCode() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(3);
        for (int i = 0; i < 3; i++) {
            sb.append(letters.charAt(random.nextInt(letters.length())));
        }
        return sb.toString();
    }

    private String generateAircraftType() {
        String[] aircraftTypes = { "Boeing 747", "Airbus A380", "Boeing 737", "Airbus A320", "Boeing 787" };
        return aircraftTypes[random.nextInt(aircraftTypes.length)];
    }

    private String generateAirlineName() {
        String[] airlineNames = { "United Airlines", "British Airways", "Air France", "Japan Airlines", "Qantas" };
        return airlineNames[random.nextInt(airlineNames.length)];
    }

    private int generateNumberOfPassengers() {
        return random.nextInt(500) + 50; // Passengers between 50 and 550
    }

    private String generateFirstName() {
        String[] firstNames = { "John", "Jane", "Mike", "Emily", "David", "Sarah", "Tom", "Lisa" };
        return firstNames[random.nextInt(firstNames.length)];
    }

    private String generateLastName() {
        String[] lastNames = { "Doe", "Smith", "Johnson", "Williams", "Brown", "Jones", "Davis", "Miller" };
        return lastNames[random.nextInt(lastNames.length)];
    }

    private String generatePhoneNumber() {
        StringBuilder sb = new StringBuilder("555-");
        for (int i = 0; i < 7; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private <T> T getRandomElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(random.nextInt(list.size()));
    }

    private <T> List<T> getRandomElements(List<T> list, int numberOfElements) {
        List<T> randomElements = new ArrayList<>();
        List<T> copy = new ArrayList<>(list); // Create a copy to avoid modifying the original list

        for (int i = 0; i < numberOfElements; i++) {
            if (copy.isEmpty()) {
                break; // If the copy is empty, we've used all the elements
            }
            int index = random.nextInt(copy.size());
            randomElements.add(copy.remove(index));
        }
        return randomElements;
    }
}