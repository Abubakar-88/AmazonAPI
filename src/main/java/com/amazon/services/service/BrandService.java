package com.amazon.services.service;

import com.amazon.dto.brand.BrandRequestDTO;
import com.amazon.dto.brand.BrandResponseDTO;

import java.util.List;

public interface BrandService {
    BrandResponseDTO createBrand(BrandRequestDTO requestDTO);

    BrandResponseDTO updateBrand(Long id, BrandRequestDTO requestDTO);

    BrandResponseDTO getBrandById(Long id);

    List<BrandResponseDTO> getAllBrands();

    void deleteBrand(Long id);
}
