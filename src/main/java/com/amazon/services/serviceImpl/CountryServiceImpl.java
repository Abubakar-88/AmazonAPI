package com.amazon.services.serviceImpl;

import com.amazon.entity.Country;
import com.amazon.entity.State;
import com.amazon.exception.ResourceNotFoundException;
import com.amazon.repository.CountryRepository;
import com.amazon.repository.StateRepository;
import com.amazon.services.service.CountryService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {
    private static final Logger log = LoggerFactory.getLogger(CountryServiceImpl.class);

    // Repository to access country data from the database.
    private final CountryRepository repo;
    private StateRepository stateRepository;

    // Constructor for dependency injection.
    public CountryServiceImpl(CountryRepository repo) {
        this.repo = repo;
    }

    // Fetches all countries from the database, sorted by name in ascending order.
    @Override
    public List<Country> getAllCountries() {
        return repo.findAllByOrderByNameAsc();
    }

    // Saves a country to the database.
    @Override
    public Country saveCountry(Country country) {
        return repo.save(country);
    }

    // Deletes a country by its ID if it exists.
    @Override
    public boolean deleteCountryById(Integer id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    // Retrieves a country by its ID.
    @Override
    public Optional<Country> getCountryById(Integer id) {
        return repo.findById(id);
    }

    // Updates an existing country with new data.
    @Override
    public boolean updateCountry(Integer id, Country country) {
        if (repo.existsById(id)) {
            country.setId(id);
            repo.save(country);
            return true;
        }
        return false;
    }

    // Retrieves a country along with its associated states.
    @Override
    @Transactional
    public Country getCountryWithStates(Integer id) {
        // Find the country by ID and throw an exception if not found
        Country country = repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Country not found for ID: " + id));

        // Print the number of states (no need to manually initialize due to the transaction)
        System.out.println("Number of states: " + country.getStates().size());

        return country;
    }

    @Transactional
    public void addStatesToCountry(Integer countryId, List<State> states) {
        // Fetch the country by ID
        Country country = repo.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found"));

        // Set the country for each state and save them
        for (State state : states) {
            state.setCountry(country);
            stateRepository.save(state);
        }
    }

}
