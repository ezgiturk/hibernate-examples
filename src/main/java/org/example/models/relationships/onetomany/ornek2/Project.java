package org.example.models.relationships.onetomany.ornek2;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Task> tasks = new HashSet<>();

    public Project(String name, String description, ProjectStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
        task.setProject(this);
    }
}
