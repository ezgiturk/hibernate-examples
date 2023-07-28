package org.example.models.relationships.onetomany;

import jakarta.persistence.*;

import java.time.LocalDate;


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

    public Order() {
    }

    public Order(String orderNumber, LocalDate orderTime) {
        this.orderNumber = orderNumber;
        this.orderTime = orderTime;
    }

    public Long getId() {
        return id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
