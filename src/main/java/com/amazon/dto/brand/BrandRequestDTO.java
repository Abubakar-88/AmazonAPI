package com.amazon.dto.brand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class BrandRequestDTO {
    @NotBlank(message = "Brand name is required and cannot be blank.")
    @Size( min =2 , max = 45, message = "Brand name between 2 to 45 characters.")
    private String name;

    @Size(max = 128, message = "Logo URL cannot exceed 128 characters.")
    private String logo;

    private Set<Long> categoryIds;

    public BrandRequestDTO(String name, String logo, Set<Long> categoryIds) {
        this.name = name;
        this.logo = logo;
        this.categoryIds = categoryIds;
    }

    public BrandRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Set<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
