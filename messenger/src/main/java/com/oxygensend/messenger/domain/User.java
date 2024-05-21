package com.oxygensend.messenger.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String email;

    public User() {
    }

    public User(String id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public String surname() {
        return surname;
    }

    public String setSurname(String surname) {
        this.surname = surname;
        return surname;
    }

    public String email() {
        return email;
    }

    public String setEmail(String email) {
        this.email = email;
        return email;
    }

    public String fullName() {
        return name + " " + surname;
    }

    public String toString() {
        return "User{id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + "}";

    }

}
