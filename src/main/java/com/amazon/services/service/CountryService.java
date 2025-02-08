package com.amazon.services.service;


import com.amazon.dto.country.CountryRequestDTO;
import com.amazon.dto.country.CountryResponseDTO;
import com.amazon.dto.state.StateRequestDTO;
import com.amazon.dto.state.StateResponseDTO;

import java.util.List;

public interface CountryService {
    CountryResponseDTO saveCountry(CountryRequestDTO countryRequestDTO);

    CountryResponseDTO updateCountry(Integer countryId, CountryRequestDTO requestDTO);

    List<StateResponseDTO> getAllStatesByCountryId(Integer countryId);

    // ✅ Get a country by ID
    CountryResponseDTO getCountryById(Integer countryId);

    // ✅ Delete a country by ID
    void deleteCountry(Integer countryId);

    // ✅ Add a state to a specific country
    StateResponseDTO addStateToCountry(Integer countryId, StateRequestDTO stateRequestDTO);

    // ✅ Update an existing state in a country
    StateResponseDTO updateState(Integer stateId, StateRequestDTO stateRequestDTO);

    // ✅ Delete a state by ID
    void deleteState(Integer stateId);

    // ✅ Get all countries
    List<CountryResponseDTO> getAllCountries();


}
