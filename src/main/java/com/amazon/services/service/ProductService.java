package com.amazon.services.service;

import com.amazon.dto.product.ProductRequestDTO;
import com.amazon.dto.product.ProductResponseDTO;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> listAllProducts();
    List<ProductResponseDTO> listProductsByPage(int pageNum, int pageSize, List<String> sortFields, List<Sort.Direction> directions);
    ProductResponseDTO saveProduct(ProductRequestDTO productRequestDTO);
    ProductResponseDTO updateProduct(Integer id, ProductRequestDTO productRequestDTO);
    ProductResponseDTO getProductById(Integer productId);
    void deleteProduct(Integer productId);
    List<ProductResponseDTO> searchProductsByName(String name);
    List<ProductResponseDTO> getProductsByCategory(Integer categoryId);




}
