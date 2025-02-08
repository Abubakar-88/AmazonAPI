package com.amazon.controller;


import com.amazon.dto.country.CountryRequestDTO;
import com.amazon.dto.country.CountryResponseDTO;
import com.amazon.dto.state.StateRequestDTO;
import com.amazon.dto.state.StateResponseDTO;
import com.amazon.services.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PostMapping
    public ResponseEntity<Object> addCountry(@RequestBody CountryRequestDTO countryRequestDTO){
        CountryResponseDTO responseDTO = countryService.saveCountry(countryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{countryId}")
    public ResponseEntity<CountryResponseDTO> updateCountry(
            @PathVariable Integer countryId,
            @RequestBody CountryRequestDTO requestDTO) {
        CountryResponseDTO responseDTO = countryService.updateCountry(countryId, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{countryId}/states")
    public ResponseEntity<List<StateResponseDTO>> getAllStatesByCountry(@PathVariable Integer countryId) {
        List<StateResponseDTO> states = countryService.getAllStatesByCountryId(countryId);
        return ResponseEntity.ok(states);
    }

    // ✅ Get all countries
    @GetMapping
    public ResponseEntity<List<CountryResponseDTO>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    // ✅ Get a country by ID
    @GetMapping("/{id}")
    public ResponseEntity<CountryResponseDTO> getCountryById(@PathVariable Integer id) {
        return ResponseEntity.ok(countryService.getCountryById(id));
    }


    // ✅ Delete a country
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Integer id) {
        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();
    }



    // ✅ Add a state to a country (******Have a issue)
    @PostMapping("/{countryId}/states")
    public ResponseEntity<StateResponseDTO> addStateToCountry(@PathVariable Integer countryId, @RequestBody StateRequestDTO stateRequestDTO) {
        StateResponseDTO createdState = countryService.addStateToCountry(countryId, stateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdState);
    }

    // ✅ Update an existing state
    @PutMapping("/states/{stateId}")
    public ResponseEntity<StateResponseDTO> updateState(@PathVariable Integer stateId, @RequestBody StateRequestDTO stateRequestDTO) {
        StateResponseDTO updatedState = countryService.updateState(stateId, stateRequestDTO);
        return ResponseEntity.ok(updatedState);
    }

    // ✅ Delete a state
    @DeleteMapping("/states/{stateId}")
    public ResponseEntity<Void> deleteState(@PathVariable Integer stateId) {
        countryService.deleteState(stateId);
        return ResponseEntity.noContent().build();
    }
}
