package org.example.models.relationships.manytomany.ornek2;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@ToString
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


    public Course(String name, String instructor, int credit) {
        this.name = name;
        this.instructor = instructor;
        this.credit = credit;
    }

}
