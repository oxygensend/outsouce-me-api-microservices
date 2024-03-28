package com.oxygensened.userprofile.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class JobPositionBuilder {
    private Long id;
    private FormOfEmployment formOfEmployment;
    private User individual;
    private String name;
    private String description;
    private boolean active;
    private Company company;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;


    public JobPositionBuilder id(Long id) {
        this.id = id;
        return this;
    }


    public JobPositionBuilder formOfEmployment(FormOfEmployment formOfEmployment) {
        this.formOfEmployment = formOfEmployment;
        return this;
    }

    public JobPositionBuilder individual(User individual) {
        this.individual = individual;
        return this;
    }

    public JobPositionBuilder name(String name) {
        this.name = name;
        return this;
    }

    public JobPositionBuilder description(String description) {
        this.description = description;
        return this;
    }

    public JobPositionBuilder active(boolean active) {
        this.active = active;
        return this;
    }

    public JobPositionBuilder company(Company company) {
        this.company = company;
        return this;
    }

    public JobPositionBuilder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public JobPositionBuilder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public JobPositionBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public JobPositionBuilder updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public JobPosition build() {
        return new JobPosition(id, formOfEmployment, individual, name, description, active, company, startDate, endDate, createdAt, updatedAt);
    }

}
