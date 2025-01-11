package com.amazon.repository;

import com.amazon.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findByNameContainingIgnoreCase(String name);
    Boolean existsByName(String name);
}
