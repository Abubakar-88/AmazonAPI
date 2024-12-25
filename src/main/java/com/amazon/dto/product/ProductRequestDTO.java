package com.amazon.dto.product;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductRequestDTO {

    @NotBlank(message = "Product name is required and cannot be blank.")
    @Size(min = 3, max = 255, message = "Product name between 3 to 255 characters.")
    private String name;

    @NotBlank(message = "Description is required and cannot be blank.")
    @Size(max = 4096, message = "Description cannot exceed 4096 characters.")
    private String description;

    @NotNull(message = "Price is required.")
    @Positive(message = "Price must be greater than 0.")
    private Float price;

    @NotNull(message = "Cost is required.")
    @PositiveOrZero(message = "Cost must be 0 or greater.")
    private Float cost;

    @PositiveOrZero(message = "Discount percent must be 0 or greater.")
    @Max(value = 100, message = "Discount percent cannot exceed 100%.")
    private Float discountPercent;

    @NotBlank(message = "Main image URL is required.")
    private String mainImage;

    @NotNull(message = "Category ID is required.")
    private Long categoryId;

    @NotNull(message = "Brand ID is required.")
    private Long brandId;

    private boolean enabled;

    private boolean inStock;
}
