package com.amazon.controller;

import com.amazon.dto.category.CategoryRequestDTO;
import com.amazon.dto.category.CategoryResponseDTO;
import com.amazon.services.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequestDTO requestDTO){
            CategoryResponseDTO responseDTO  = categoryService.createCategory(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategoris(){
      List<CategoryResponseDTO> categoris = categoryService.getAllCategories();
      return ResponseEntity.ok(categoris);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO requestDTO){
          CategoryResponseDTO responseDTO=   categoryService.updateCategory(id, requestDTO);
          return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        categoryService.deleteCategory(id);
       // return ResponseEntity.ok().build();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{brandId}/categories")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategoriesOfBrand(@PathVariable Long brandId) {
        List<CategoryResponseDTO> categories = categoryService.getCategoriesByBrandId(brandId);
        return ResponseEntity.ok(categories);
    }





}
