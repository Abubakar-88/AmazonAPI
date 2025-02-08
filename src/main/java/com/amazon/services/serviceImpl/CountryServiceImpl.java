package com.amazon.services.serviceImpl;

import com.amazon.dto.country.CountryRequestDTO;
import com.amazon.dto.country.CountryResponseDTO;
import com.amazon.dto.state.StateRequestDTO;
import com.amazon.dto.state.StateResponseDTO;
import com.amazon.entity.Country;
import com.amazon.entity.State;
import com.amazon.exception.ResourceNotFoundException;
import com.amazon.repository.CountryRepository;
import com.amazon.repository.StateRepository;
import com.amazon.services.service.CountryService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CountryResponseDTO saveCountry(CountryRequestDTO countryRequestDTO) {

        Country country = modelMapper.map(countryRequestDTO, Country.class);

        Set<State> states = new HashSet<>();
        if (countryRequestDTO.getStates() != null && !countryRequestDTO.getStates().isEmpty()) {
            for (StateRequestDTO stateRequestDTO : countryRequestDTO.getStates()) {
                State state = modelMapper.map(stateRequestDTO, State.class);
                // set the country for each state
                state.setCountry(country);
                // add the state to the Country's state
                states.add(state);
            }
        }

        country.setStates(states);
        Country saveCountry = countryRepository.save(country);
        return modelMapper.map(saveCountry, CountryResponseDTO.class);

    }

    @Transactional
    @Override
    public CountryResponseDTO updateCountry(Integer countryId, CountryRequestDTO requestDTO) {
        // Fetch the existing country
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ID: " + countryId));

        // Update the country name and code
        country.setName(requestDTO.getName());
        country.setCode(requestDTO.getCode()); // Add code field update here
//=============================================================================================
//            // Clear existing states and reassign new ones
//            country.getStates().clear();
//
//
//            // Map and set new states
//            if (requestDTO.getStates() != null) {
//                for (StateRequestDTO stateDTO : requestDTO.getStates()) {
//                    State state = new State();
//                    state.setName(stateDTO.getName());
//                    state.setCountry(country); // Maintain the bidirectional relationship
//                    country.getStates().add(state);
//                }
//            }
//=================================================================================================
//            // Maintain existing states in a map for easy lookup
//            Map<String, State> existingStatesMap = country.getStates().stream()
//                    .collect(Collectors.toMap(State::getName, state -> state));
//
//            // Add or update states from the request
//            Set<State> updatedStates = new HashSet<>();
//            for (StateRequestDTO stateDTO : requestDTO.getStates()) {
//                State state = existingStatesMap.getOrDefault(stateDTO.getName(), new State());
//                state.setName(stateDTO.getName());
//                state.setCountry(country); // Maintain bidirectional relationship
//                updatedStates.add(state);
//            }
//==================================================================================================

        // Create or update states from the request
        Set<State> updatedStates = requestDTO.getStates().stream()
                .map(stateDTO -> {
                    // Check if the state already exists or create a new one
                    State state = country.getStates().stream()
                            .filter(existingState -> existingState.getName().equals(stateDTO.getName()))
                            .findFirst()
                            .orElse(new State());
                    state.setName(stateDTO.getName());
                    state.setCountry(country); // Maintain bidirectional relationship
                    return state;
                })
                .collect(Collectors.toSet());

// Update the country's states with the new set
        country.setStates(updatedStates);

        // Save the updated country
        Country updatedCountry = countryRepository.save(country);

        // Map to response DTO
        CountryResponseDTO responseDTO = modelMapper.map(updatedCountry, CountryResponseDTO.class);
        responseDTO.setStates(updatedCountry.getStates().stream()
                .map(state -> modelMapper.map(state, StateResponseDTO.class))
                .collect(Collectors.toList()));

        return responseDTO;
    }

//    When clear() Might Be Acceptable
//    You have a simple use case where you always want to completely replace the child
//    entities with a new set.
//    Performance is not a concern, and data consistency is less critical.
//    You explicitly understand and control how clear() affects the database (e.g., cascade settings).

    @Override
    public List<StateResponseDTO> getAllStatesByCountryId(Integer countryId) {
        // Fetch the country by its ID
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ID: " + countryId));

        // Map the states to response DTOs
        return country.getStates().stream()
                .map(state -> modelMapper.map(state, StateResponseDTO.class))
                .collect(Collectors.toList());
    }

    // ✅ Get a country by ID
    @Override
    public CountryResponseDTO getCountryById(Integer countryId) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + countryId));
        return modelMapper.map(country, CountryResponseDTO.class);
    }

    // ✅ Delete a country by ID
    @Override
    public void deleteCountry(Integer countryId) {
        if (!countryRepository.existsById(countryId)) {
            throw new ResourceNotFoundException("Country not found with id: " + countryId);
        }
        countryRepository.deleteById(countryId);
    }

    // ✅ Get all countries
    @Override
    public List<CountryResponseDTO> getAllCountries() {
        return countryRepository.findAll()
                .stream()
                .map(country -> modelMapper.map(country, CountryResponseDTO.class))
                .collect(Collectors.toList());
    }

    // ✅ Add a state to a specific country
    @Override
    public StateResponseDTO addStateToCountry(Integer countryId, StateRequestDTO stateRequestDTO) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + countryId));

        State state = modelMapper.map(stateRequestDTO, State.class);
        state.setCountry(country);
        State savedState = stateRepository.save(state);

        return modelMapper.map(savedState, StateResponseDTO.class);
    }

    // ✅ Update an existing state
    @Override
    public StateResponseDTO updateState(Integer stateId, StateRequestDTO stateRequestDTO) {
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new ResourceNotFoundException("State not found with id: " + stateId));

        state.setName(stateRequestDTO.getName());
        State updatedState = stateRepository.save(state);

        return modelMapper.map(updatedState, StateResponseDTO.class);
    }

    // ✅ Delete a state by ID
    @Override
    public void deleteState(Integer stateId) {
        if (!stateRepository.existsById(stateId)) {
            throw new ResourceNotFoundException("State not found with id: " + stateId);
        }
        stateRepository.deleteById(stateId);
    }

}




