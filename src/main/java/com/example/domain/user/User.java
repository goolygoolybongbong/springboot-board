package com.example.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String email;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String name, String email, int age, String description, Role role) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.role = role;
        this.description = description;
    }

    public void update(String email, String description, Role role) {
        this.email = email;
    }

    public void getOlder() {
        this.age++;
    }

    
}
