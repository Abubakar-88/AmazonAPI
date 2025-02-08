package com.amazon.services.service;

import com.amazon.dto.category.CategoryRequestDTO;
import com.amazon.dto.category.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);
    List<CategoryResponseDTO> addCategories(List<CategoryRequestDTO> requestDTOs);
    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO);
    CategoryResponseDTO getCategoryById(Long id);
    List<CategoryResponseDTO> getCategoriesByBrandId(Long brandId);
    void deleteCategory(Long id);
}
