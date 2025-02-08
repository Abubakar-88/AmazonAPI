package com.amazon.repository;

import com.amazon.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
    
    // ✅ Find all states by country ID
    List<State> findByCountryId(Integer countryId);

    // ✅ Check if a state with a given name exists in a specific country
    boolean existsByNameAndCountryId(String name, Integer countryId);
}
