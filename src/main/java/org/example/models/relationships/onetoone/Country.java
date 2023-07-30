package org.example.models.relationships.onetoone;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Country {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne
    private Capital capital;

    public Country(String name) {
        this.name = name;
    }
}
