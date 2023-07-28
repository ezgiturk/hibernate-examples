package org.example.models.relationships.onetoone;

import jakarta.persistence.*;

@Entity
public class Capital {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Country country;

    public Capital() {
    }

    public Capital(String name, Country country) {
        this.name = name;
        this.country = country;
    }
}