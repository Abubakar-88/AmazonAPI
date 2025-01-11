package com.amazon.controller;

import com.amazon.dto.product.ProductRequestDTO;
import com.amazon.dto.product.ProductResponseDTO;
import com.amazon.services.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductRequestDTO dto) {
        productService.saveProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.listAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProductsByName(@RequestParam String name) {
        List<ProductResponseDTO> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/page")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByPage(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam List<String> sortFields,
            @RequestParam List<Sort.Direction> directions
            ){
       List<ProductResponseDTO> products = productService.listProductsByPage(pageNum,pageSize,sortFields,directions);
    return ResponseEntity.ok(products);
    }

}
