package com.amazon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Entity

public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false, length = 45, unique = true)
    private String name;

    @Column(nullable = true, length = 128)
    private String logo;

    @ManyToMany
    @JoinTable(
            name = "brands_categories", // Ensure this table exists in your database
            joinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "id"), // Map brand's id column
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id") // Map category's id column
    )
    private Set<Category> categories = new HashSet<>();

    public Brand() {
    }

    public Brand(Integer id, String name, String logo, Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
