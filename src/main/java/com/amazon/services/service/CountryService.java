package com.amazon.services.service;


import com.amazon.entity.Country;
import com.amazon.entity.State;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    List<Country> getAllCountries();
    Country saveCountry(Country country);
    boolean deleteCountryById(Integer id);
    Optional<Country> getCountryById(Integer id);
    boolean updateCountry(Integer id, Country country);
    Country getCountryWithStates(Integer id);
    void addStatesToCountry(Integer countryId, List<State> states);

}