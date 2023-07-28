package org.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String model;

    private String color;

    public Car(String type, String model, String color) {
        this.type = type;
        this.model = model;
        this.color = color;
    }


}
