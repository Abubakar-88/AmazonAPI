package com.amazon.services.service;

import com.amazon.dto.brand.BrandRequestDTO;
import com.amazon.dto.brand.BrandResponseDTO;

import java.util.List;

public interface BrandService {
     BrandResponseDTO createBrand(BrandRequestDTO resquestDTO);
     List<BrandResponseDTO> getAllBrands();

     BrandResponseDTO getBrandById(Long id);



     void deleteBrand(Long id);

     BrandResponseDTO updateBrand(Long id, BrandRequestDTO brandDetails) ;


}
