package com.amazon.dto.category;

public class CategoryRequestDTO {

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
