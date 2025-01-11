package com.amazon.repository;

import com.amazon.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
   public List<Country> findAllByOrderByNameAsc();
   @Query("SELECT c FROM Country c JOIN FETCH c.states WHERE c.id = :id")
   Optional<Country> findByIdWithStates(Integer id);

}