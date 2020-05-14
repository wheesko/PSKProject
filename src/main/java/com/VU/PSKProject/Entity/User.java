package com.VU.PSKProject.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserAuthority userAuthority;

    @OneToOne(mappedBy = "user")
    private Worker workerInstance;

    public User(String email, String password, UserAuthority userAuthority) {
        this.email = email;
        this.password = password;
        this.userAuthority = userAuthority;
    }

    public User() {
    }
}