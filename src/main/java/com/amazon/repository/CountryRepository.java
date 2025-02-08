package com.amazon.repository;

import com.amazon.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByCode(String code);
    List<Country> findAllByOrderByNameAsc();
}
