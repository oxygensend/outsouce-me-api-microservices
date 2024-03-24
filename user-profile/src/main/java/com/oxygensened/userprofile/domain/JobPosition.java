package com.oxygensened.userprofile.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class JobPosition {

    @Id
    private UUID id;
    @Column(nullable = false)
    private String formOfEmployment;
    @ManyToOne
    private User individual;
    @Column(nullable = false)
    private String name;
    @Column(length = 1000)
    private String description;
    @Column(nullable = false)
    private boolean active;
    @ManyToOne
    private Company company;
    @Column(nullable = false)
    private LocalDate startDate;
    private LocalDate endDate;
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    public JobPosition() {
    }

    public JobPosition(UUID id, String formOfEmployment, User individual, String name, String description, boolean active, Company company, LocalDate startDate, LocalDate endDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.formOfEmployment = formOfEmployment;
        this.individual = individual;
        this.name = name;
        this.description = description;
        this.active = active;
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String formOfEmployment() {
        return formOfEmployment;
    }

    public void setFormOfEmployment(String formOfEmployment) {
        this.formOfEmployment = formOfEmployment;
    }

    public User individual() {
        return individual;
    }

    public void setIndividual(User individual) {
        this.individual = individual;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean active() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Company company() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
