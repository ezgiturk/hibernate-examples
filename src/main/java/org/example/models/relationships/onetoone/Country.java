package org.example.models.relationships.onetoone;

import jakarta.persistence.*;

@Entity
public class Country {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne
    private Capital capital;

    public Long getId() {
        return id;
    }

    public void setCapital(Capital capital) {
        this.capital = capital;
    }

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }
}
