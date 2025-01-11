package com.amazon.dto.product;

import jakarta.validation.constraints.*;

public class ProductRequestDTO {
    @NotBlank(message = "Product name is required and cannot be blank.")
    @Size(min = 3, max = 255, message = "Product name between 3 to 255 characters.")
    private String name;
    @NotBlank(message = "Description is required and cannot be blank.")
    @Size(max = 4096, message = "Description cannot exceed 4096 characters.")
    private String description;
    private boolean enabled;
    private boolean inStock;
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be grater than 0.")
    private float price;
    @NotNull(message = "Cost is required")
    @PositiveOrZero(message = "Cost must be 0 or grater.")
    private float cost;
    @PositiveOrZero(message = "Discount percent must be 0 or grater")
    @Max(value = 100, message = "Discount percent cannot be exceed 100%")
    private float discountPercent;
    @NotBlank(message = "Main image URL is required.")
    private String mainImage;
    @NotNull(message = "Category Id is required")
    private Long categoryId;
    @NotNull(message = "Brand Id is requird")
    private Long brandId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
