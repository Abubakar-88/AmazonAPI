package com.amazon.repository;

import com.amazon.entity.Country;
import com.amazon.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Integer> {
    public List<State> findByCountryOrderByNameAsc(Country country);
}