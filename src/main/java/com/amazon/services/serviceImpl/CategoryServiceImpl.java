package com.amazon.services.serviceImpl;

import com.amazon.dto.brand.BrandResponseDTO;
import com.amazon.dto.category.CategoryRequestDTO;
import com.amazon.dto.category.CategoryResponseDTO;
import com.amazon.entity.Brand;
import com.amazon.entity.Category;
import com.amazon.exception.ResourceAlreadyExistsException;
import com.amazon.exception.ResourceNotFoundException;
import com.amazon.repository.BrandRepository;
import com.amazon.repository.CategoryRepository;
import com.amazon.services.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

     @Autowired
     CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;

     @Autowired
     ModelMapper modelMapper;


    // Create a new category
    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {
        if (requestDTO.getName() == null || requestDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or blank.");
        }
        if (categoryRepository.existsByName(requestDTO.getName())) {
            throw new ResourceAlreadyExistsException("Category with name '" + requestDTO.getName() + "' already exists.");
        }
        Category category = modelMapper.map(requestDTO, Category.class); // Map requestDTO to entity
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryResponseDTO.class); // Map saved entity to responseDTO
    }

    // Get all categories
    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Update a category
    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + id + " not found."));
        modelMapper.map(requestDTO, category); // Map requestDTO to existing entity
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryResponseDTO.class); // Map updated entity to responseDTO
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
        return modelMapper.map(category, CategoryResponseDTO.class);
    }
    @Override
    public List<CategoryResponseDTO> getCategoriesByBrandId(Long brandId) {
        // Find the brand by ID
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with ID: " + brandId));

        // Get categories from the brand
        Set<Category> categories = brand.getCategories();

        // Map categories to response DTOs
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Brand not found with ID: " + id);
        }
        categoryRepository.deleteById(id);
    }


}
