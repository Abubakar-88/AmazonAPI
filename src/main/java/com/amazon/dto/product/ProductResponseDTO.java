package com.amazon.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private Integer id;

    private String name;

    private String description;

    private Float price;

    private Float cost;

    private Float discountPercent;

    private String mainImage;

    private boolean enabled;

    private boolean inStock;

    private String categoryName; // Category name for better readability in response

    private String brandName; // Brand name for better readability in response
}

