package org.example.models.relationships.onetomany;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String address;

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders = new HashSet<>();

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setCustomer(this);
    }

    public Customer() {
    }

    public Customer(String username, String email, String address) {
        this.username = username;
        this.email = email;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public Set<Order> getOrders() {
        return orders;
    }
}
