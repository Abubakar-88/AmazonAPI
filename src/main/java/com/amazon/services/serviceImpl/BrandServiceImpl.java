package com.amazon.services.serviceImpl;

import com.amazon.dto.brand.BrandRequestDTO;
import com.amazon.dto.brand.BrandResponseDTO;
import com.amazon.entity.Brand;
import com.amazon.entity.Category;
import com.amazon.exception.ResourceAlreadyExistsException;
import com.amazon.exception.ResourceNotFoundException;
import com.amazon.repository.BrandRepository;
import com.amazon.repository.CategoryRepository;
import com.amazon.services.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {


    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public BrandResponseDTO createBrand(BrandRequestDTO requestDTO) {

        if (brandRepository.existsByName(requestDTO.getName())) {
            throw new ResourceAlreadyExistsException("Brand with name '" + requestDTO.getName() + "' already exists.");
        }

        Brand brand = modelMapper.map(requestDTO, Brand.class);
        // Map category IDs to Category entities
        Set<Category> categories = categoryRepository.findAllById(requestDTO.getCategoryIds())
                .stream()
                .collect(Collectors.toSet());
        brand.setCategories(categories);

        Brand savedBrand = brandRepository.save(brand);
       // Convert the updated Brand entity to a BrandResponseDTO
        return modelMapper.map(savedBrand, BrandResponseDTO.class);
    }

    @Override
    public BrandResponseDTO updateBrand(Long id, BrandRequestDTO requestDTO) {
        // Retrieve the existing Brand entity
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with ID: " + id));

        // Update the entity fields using ModelMapper
        modelMapper.map(requestDTO, existingBrand);

        // Map category IDs from the request to Category entities
        Set<Category> categories = categoryRepository.findAllById(requestDTO.getCategoryIds())
                .stream()
                .collect(Collectors.toSet());
        existingBrand.setCategories(categories);

        // Save the updated Brand entity
        Brand updatedBrand = brandRepository.save(existingBrand);

        // Convert the updated Brand entity to a BrandResponseDTO
        return modelMapper.map(updatedBrand, BrandResponseDTO.class);
    }
    @Override
    public BrandResponseDTO getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with ID: " + id));
        return modelMapper.map(brand, BrandResponseDTO.class);
    }

    @Override
    public List<BrandResponseDTO> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(brand -> modelMapper.map(brand, BrandResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Brand not found with ID: " + id);
        }
        brandRepository.deleteById(id);
    }

}
