package com.amazon.dto.brand;

import java.util.Set;

public class BrandRequestDTO {

    private String name;
    private String logo;
    private Set<Long> categoryIds;

    public BrandRequestDTO() {
    }

    public BrandRequestDTO(String name, String logo, Set<Long> categoryIds) {
        this.name = name;
        this.logo = logo;
        this.categoryIds = categoryIds;
    }

    public Set<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Set<Long> categoryIds) {
        this.categoryIds = categoryIds;
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
}
