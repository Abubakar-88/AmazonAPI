package com.amazon.services.serviceImpl;

import com.amazon.dto.brand.BrandResponseDTO;
import com.amazon.dto.category.CategoryResponseDTO;
import com.amazon.dto.product.ProductRequestDTO;
import com.amazon.dto.product.ProductResponseDTO;
import com.amazon.entity.Brand;
import com.amazon.entity.Category;
import com.amazon.entity.Product;
import com.amazon.exception.DuplicateFieldException;
import com.amazon.exception.ResourceNotFoundException;
import com.amazon.repository.ProductRepository;
import com.amazon.services.service.BrandService;
import com.amazon.services.service.CategoryService;
import com.amazon.services.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductResponseDTO> listAllProducts() {
        return productRepository.findAll().stream().map(
                product -> modelMapper.map(product,ProductResponseDTO.class))
                .collect(Collectors.toList()

        );

    }

    @Override
    public List<ProductResponseDTO> listProductsByPage(int pageNum, int pageSize, List<String> sortFields, List<Sort.Direction> directions) {
        List<Sort.Order> orders = new ArrayList<>();
        for(int i=0; i<sortFields.size(); i++){
           orders.add(new Sort.Order(directions.get(i),sortFields.get(i))) ;
        }
        Pageable pageable = PageRequest.of(pageNum-1, pageSize, Sort.by(orders));
        return productRepository.findAll(pageable).stream()
                .map(product -> modelMapper.map(product,ProductResponseDTO.class))
                .collect(Collectors.toList());

       }

    @Override
    public ProductResponseDTO saveProduct(ProductRequestDTO productRequestDTO) {
        if(productRepository.existsByName(productRequestDTO.getName())){
            throw new DuplicateFieldException("Product name ", productRequestDTO.getName());
        }
        Product product = new Product();
        mapDTOtoEntity(productRequestDTO,product);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductResponseDTO.class);
    }

    @Override
    public ProductResponseDTO updateProduct(Integer id, ProductRequestDTO productRequestDTO) {
        return null;
    }

    @Override
    public ProductResponseDTO getProductById(Integer productId) {
       Product product = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product not found with id: "+productId));
        return modelMapper.map(product,ProductResponseDTO.class);
    }

    @Override
    public void deleteProduct(Integer productId) {

    }

    @Override
    public List<ProductResponseDTO> searchProductsByName(String name) {
        return productRepository.findAll().stream().filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                .map(product -> modelMapper.map(product,ProductResponseDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<ProductResponseDTO> getProductsByCategory(Integer categoryId) {
        return null;
    }

    private void mapDTOtoEntity(ProductRequestDTO dto, Product product) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCost(dto.getCost());
        product.setDiscountPercent(dto.getDiscountPercent());
        product.setMainImage(dto.getMainImage());
        product.setEnabled(dto.isEnabled());
        product.setInStock(dto.isInStock());

        CategoryResponseDTO categoryResponseDTO = categoryService.getCategoryById(dto.getCategoryId());
        Category category = modelMapper.map(categoryResponseDTO, Category.class);
        product.setCategory(category);

        BrandResponseDTO responseDTO = brandService.getBrandById(dto.getBrandId());
        Brand brand = modelMapper.map(responseDTO, Brand.class);
        product.setBrand(brand);


    }
}
