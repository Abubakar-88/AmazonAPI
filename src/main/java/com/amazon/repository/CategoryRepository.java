package com.amazon.repository;

import com.amazon.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

//    @Query("SELECT c FROM Category c WHERE c.brand.id = :brandId")
//    List<Category> findCategoriesByBrandId(@Param("brandId") Integer brandId);
}
