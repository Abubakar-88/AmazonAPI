package com.amazon.controller;


import com.amazon.dto.category.CategoryRequestDTO;
import com.amazon.dto.category.CategoryResponseDTO;
import com.amazon.exception.ResourceNotFoundException;
import com.amazon.services.service.CategoryService;
import com.amazon.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    // Create a new category
    @PostMapping
    public ResponseEntity<Object> createCategory(
            @Validated @RequestBody CategoryRequestDTO requestDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ValidationUtil.validateRequest(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
       // try {
            CategoryResponseDTO responseDTO = categoryService.createCategory(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
//        } catch (Exception e) {
//            throw new ResourceAlreadyExistsException(e.getMessage());
//        }

    }

    // Get all categories
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // Update a category
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @Validated @RequestBody CategoryRequestDTO requestDTO) {
       // try {
            CategoryResponseDTO responseDTO = categoryService.updateCategory(id, requestDTO);
            return ResponseEntity.ok(responseDTO);
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoryService.getCategoryById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}