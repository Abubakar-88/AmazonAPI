package com.amazon.controller;

import com.amazon.dto.brand.BrandRequestDTO;
import com.amazon.dto.brand.BrandResponseDTO;
import com.amazon.dto.category.CategoryResponseDTO;
import com.amazon.services.service.BrandService;
import com.amazon.services.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

   @Autowired
   private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<BrandResponseDTO> createBrand(@RequestBody BrandRequestDTO resquestDTO){
            BrandResponseDTO  brandResponseDTO= brandService.createBrand(resquestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(brandResponseDTO);
    }

    @GetMapping("/{brandId}/categories")
    public ResponseEntity<List<CategoryResponseDTO>> getCategoriesByBrand(@PathVariable Long brandId){
       List<CategoryResponseDTO> categories = categoryService.getCategoriesByBrandId(brandId);
       return ResponseEntity.ok(categories);
    }

    @GetMapping
    public ResponseEntity<List<BrandResponseDTO>> getAllBrands() {
        List<BrandResponseDTO> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> getBrandById(@PathVariable Long id) {
        BrandResponseDTO brand = brandService.getBrandById(id);
        return ResponseEntity.ok(brand);
    }


    @PutMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> updateBrand(@PathVariable Long id,
                                                        @RequestBody BrandRequestDTO requestDTO) {
        BrandResponseDTO updatedBrand = brandService.updateBrand(id, requestDTO);
        return ResponseEntity.ok(updatedBrand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }

}
