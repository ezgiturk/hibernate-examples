package org.example.models.relationships.onetoone.ornek2;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String username;

    @OneToOne
    private UserDetail userDetail;

    public User(String username) {
        this.username = username;
    }
}
