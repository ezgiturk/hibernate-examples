package org.example.models.relationships.onetoone.ornek2;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class UserDetail {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String address;
    private String number;

    @OneToOne
    private User user;

    public UserDetail(String email, String address, String number, User user) {
        this.email = email;
        this.address = address;
        this.number = number;
        this.user = user;
    }
}
