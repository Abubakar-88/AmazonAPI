package com.amazon.services.service;

import com.amazon.dto.brand.BrandResponseDTO;
import com.amazon.dto.category.CategoryRequestDTO;
import com.amazon.dto.category.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);
    public List<CategoryResponseDTO> getAllCategories();
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO);
    public CategoryResponseDTO getCategoryById(Long id);
    List<CategoryResponseDTO> getCategoriesByBrandId(Long brandId);
    void deleteCategory(Long id);
}
