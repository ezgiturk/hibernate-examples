package org.example.models.relationships.manytomany;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@NoArgsConstructor
@Entity(name = "Book")
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.PERSIST)
    private Set<Author> authors = new HashSet<>();

    public Book(String title) {
        this.title = title;
    }
}