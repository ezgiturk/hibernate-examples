package org.example.models.relationships.onetomany;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    @Temporal(TemporalType.DATE)
    private LocalDate orderTime;

    @ManyToOne
    private Customer customer;

    public Order(String orderNumber, LocalDate orderTime) {
        this.orderNumber = orderNumber;
        this.orderTime = orderTime;
    }

}
