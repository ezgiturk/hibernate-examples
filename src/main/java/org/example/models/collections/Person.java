package org.example.models.collections;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String surname;

    @ElementCollection
    @CollectionTable(name = "person_addresses")
    private List<String> addresses = new ArrayList<>();

    public Person() {}

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public void addAddress(String address) {
        this.addresses.add(address);
    }

    public Long getId() {
        return id;
    }

    public List<String> getAddresses() {
        return addresses;
    }
}
