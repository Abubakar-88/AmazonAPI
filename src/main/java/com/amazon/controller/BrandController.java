package com.amazon.controller;


import com.amazon.dto.brand.BrandRequestDTO;
import com.amazon.dto.brand.BrandResponseDTO;
import com.amazon.dto.category.CategoryResponseDTO;
import com.amazon.exception.ResourceNotFoundException;
import com.amazon.services.service.BrandService;
import com.amazon.services.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<BrandResponseDTO> createBrand(
            @Validated @RequestBody BrandRequestDTO requestDTO) {
        BrandResponseDTO brandResponseDTO = brandService.createBrand(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(brandResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(
            @PathVariable Long id,
            @Validated @RequestBody BrandRequestDTO requestDTO) {
        try {
            return ResponseEntity.ok(brandService.updateBrand(id, requestDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(brandService.getBrandById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<BrandResponseDTO>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }
    @GetMapping("/{brandId}/categories")
    public ResponseEntity<List<CategoryResponseDTO>> getCategoriesByBrandId(@PathVariable Long brandId) {
        List<CategoryResponseDTO> categories = categoryService.getCategoriesByBrandId(brandId);
        return ResponseEntity.ok(categories);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
