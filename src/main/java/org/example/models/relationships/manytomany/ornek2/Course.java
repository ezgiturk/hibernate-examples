package org.example.models.relationships.manytomany.ornek2;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String instructor;

    private int credit;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();


    public Course() {
    }

    public Course(String name, String instructor, int credit) {
        this.name = name;
        this.instructor = instructor;
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructor='" + instructor + '\'' +
                ", credit=" + credit +
                '}';
    }
}
