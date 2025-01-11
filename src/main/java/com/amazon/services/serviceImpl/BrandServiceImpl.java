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
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public BrandResponseDTO createBrand(BrandRequestDTO resquestDTO) {
        if (brandRepository.existsByName(resquestDTO.getName())) {
            throw new ResourceAlreadyExistsException("Brand with name " + resquestDTO.getName() + " already exists.");
        }
          Brand brand = modelMapper.map(resquestDTO, Brand.class);
        Set<Category> categories =  categoryRepository.findAllById(resquestDTO.getCategoryIds()).stream().collect(Collectors.toSet());
                 brand.setCategories(categories);

       Brand  savedBrand =   brandRepository.save(brand);
       return modelMapper.map(savedBrand, BrandResponseDTO.class);
    }

    @Override
    public List<BrandResponseDTO> getAllBrands() {
        // Fetch all brands and map them to BrandResponseDTO
        return brandRepository.findAll()
                .stream()
                .map(brand -> modelMapper.map(brand, BrandResponseDTO.class))
                .toList();
    }


    @Override
    public BrandResponseDTO getBrandById(Long id) {
        // Fetch brand by ID, throw exception if not found
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
        return modelMapper.map(brand, BrandResponseDTO.class);
    }





    @Override
    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Brand not found with id: " + id);
        }
        brandRepository.deleteById(id);
    }

    @Override
    public BrandResponseDTO updateBrand(Long id, BrandRequestDTO brandDetails) {
        // Fetch the existing brand or throw an exception if not found
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand with ID " + id + " not found."));

        // Check if the new name is already taken by another brand
        //client wants to change the name - true or false (left side of &&)
        if (!existingBrand.getName().equals(brandDetails.getName()) &&
                brandRepository.existsByName(brandDetails.getName())) {
            throw new ResourceAlreadyExistsException("Brand with name " + brandDetails.getName() + " already exists.");
        }

        // Update the brand's fields
        existingBrand.setName(brandDetails.getName());
        existingBrand.setLogo(brandDetails.getLogo());

        // Update the associated categories
        Set<Category> categories = categoryRepository.findAllById(brandDetails.getCategoryIds())
                .stream().collect(Collectors.toSet());
        existingBrand.setCategories(categories);

        // Save the updated brand and return the response DTO
        Brand updatedBrand = brandRepository.save(existingBrand);
        return modelMapper.map(updatedBrand, BrandResponseDTO.class);

    }






}




















//        Example 1: Changing the name
//        Current name: "Brand A"
//        New name: "Brand B"
//
//        !existingBrand.getName().equals(brandDetails.getName())
//        → !("Brand A".equals("Brand B"))
//        → true
//        The result is true, so the client wants to change the name.

//        Example 2: Not changing the name
//        Current name: "Brand A"
//        New name: "Brand A"
//
//        !existingBrand.getName().equals(brandDetails.getName())
//        → !("Brand A".equals("Brand A"))
//        → false
//        The result is false, so the client did not change the name.
