package com.amazon.services.serviceImpl;

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
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {

       if(categoryRepository.existsByName(requestDTO.getName())){
           throw new ResourceAlreadyExistsException("Category with name " + requestDTO.getName() + "already exists.");
       }

        Category category = modelMapper.map(requestDTO, Category.class);
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryResponseDTO.class);

    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO) {
         Category category = categoryRepository.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("Category with ID" + id + " not found."));
            modelMapper.map(requestDTO, category);
              category = categoryRepository.save(category);
              return modelMapper.map(category, CategoryResponseDTO.class);

    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID" + id + " not found."));
        return modelMapper.map(category, CategoryResponseDTO.class);
    }

    @Override
    public List<CategoryResponseDTO> getCategoriesByBrandId(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand with ID" + brandId + " not found."));
        // get categories from the brand
        Set<Category> categories = brand.getCategories();

        return categories.stream().map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
//        Category category = categoryRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundExcepton("Category with ID" + id + " not found."));

        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Category not found with ID: " + id);
        }
        categoryRepository.deleteById(id);

    }



}
