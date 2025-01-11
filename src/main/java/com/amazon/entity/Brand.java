package com.amazon.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 45, unique = true)
    private String name;
    private String logo;

    @ManyToMany
    @JoinTable(
            name = "brand_categories",
            joinColumns = @JoinColumn (name= "brand_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private Set<Category> categories = new HashSet<>();

    public Brand() {
    }

    public Brand(Long id, String name, String logo, Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.categories = categories;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
