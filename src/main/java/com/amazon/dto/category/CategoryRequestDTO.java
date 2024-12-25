package com.amazon.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class CategoryRequestDTO {

    @NotBlank(message = "Name is required and cannot be blank.")
    @Size(min = 3, max = 128, message = "Name must be between 3 and 128 characters.")
    private String name;

    private boolean enabled;

    public CategoryRequestDTO() {
    }

    public CategoryRequestDTO(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
