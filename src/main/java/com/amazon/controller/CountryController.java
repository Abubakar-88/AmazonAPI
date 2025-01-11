package com.amazon.controller;


import com.amazon.entity.Country;
import com.amazon.entity.State;
import com.amazon.exception.ResourceNotFoundException;
import com.amazon.services.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries") // Base URL for all methods in this controller
public class CountryController {

    private final CountryService countryService;

    // Constructor for dependency injection
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    // Get a list of all countries
    @GetMapping
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    // Get a single country by ID
    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Integer id) {
        return countryService.getCountryById(id)
                .map(country -> ResponseEntity.ok(country)) // If found, return 200 OK with the country
                .orElseGet(() -> ResponseEntity.notFound().build()); // If not found, return 404 Not Found
    }

    // Add a new country
    @PostMapping
    public Country addCountry(@RequestBody Country country) {
        return countryService.saveCountry(country);
    }

    // Update an existing country
    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Integer id, @RequestBody Country country) {
        boolean updated = countryService.updateCountry(id, country);
        if (updated) {
            return ResponseEntity.ok(country);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a country
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Integer id) {
        boolean deleted = countryService.deleteCountryById(id);
        if (deleted) {
            return ResponseEntity.ok().build(); // Return 200 OK if the deletion was successful
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if there was no country to delete
        }
    }

    @GetMapping("/{id}/with-states")
    public ResponseEntity<Country> getCountryWithStates(@PathVariable Integer id) {

        Country country = countryService.getCountryWithStates(id);
        return ResponseEntity.ok(country); // Return 200 OK with the country data

    }

    @PostMapping("/{id}/states")
    public ResponseEntity<String> addStatesToCountry(@PathVariable Integer id, @RequestBody List<State> states) {
        try {
            countryService.addStatesToCountry(id, states);
            return ResponseEntity.status(HttpStatus.CREATED).body("States added successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Country not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while adding states");
        }
    }
}
