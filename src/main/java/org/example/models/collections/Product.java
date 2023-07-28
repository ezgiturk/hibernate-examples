package org.example.models.collections;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ElementCollection
    @CollectionTable(name = "product_codes", joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyColumn(name = "product_name")
    @Column(name = "product_code")
    private Map<String, String> productCodes = new HashMap<>();

    public Product(String name) {
        this.name = name;
    }

    public Product() {

    }

    public Map<String, String> getProductCodes() {
        return productCodes;
    }

    public Long getId() {
        return id;
    }
}

