package com.oxygensened.userprofile.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    public University() {
    }

    public University(String name) {
        this.name = name;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
