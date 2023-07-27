package org.example.models.mappingvalue.ornek1;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String surname;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Enumerated(EnumType.ORDINAL)
    private Job job;

    @CreationTimestamp
    private Date creationDate;

    @UpdateTimestamp
    private Date lastModifiedDate;


    public Employee() {
    }

    public Employee(String name, String surname, Gender gender, Job job) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.job = job;
    }

    public Long getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender=" + gender +
                ", job=" + job +
                ", creationDate=" + creationDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}