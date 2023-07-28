package org.example.models.collections;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Staff {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String surname;

    @ElementCollection
    @CollectionTable(name = "staff_numbers")
    @OrderColumn
    private List<String> phoneNumbers = new ArrayList<>();

    public void addPhoneNumber(String number) {
        this.phoneNumbers.add(number);
    }

    public Staff() {
    }

    public Staff(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void removeFirstPhoneNumber() {
        this.phoneNumbers.remove(0);
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                '}';
    }
}
