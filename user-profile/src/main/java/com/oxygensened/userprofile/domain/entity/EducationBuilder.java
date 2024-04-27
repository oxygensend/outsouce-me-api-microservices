package com.oxygensened.userprofile.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EducationBuilder {
    private Long id;
    private University university;
    private User individual;
    private String fieldOfStudy;
    private String title;
    private String description;
    private Double grade;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt = LocalDateTime.now();

    public EducationBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public EducationBuilder university(University university) {
        this.university = university;
        return this;
    }

    public EducationBuilder individual(User individual) {
        this.individual = individual;
        return this;
    }

    public EducationBuilder fieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
        return this;
    }

    public EducationBuilder title(String title) {
        this.title = title;
        return this;
    }

    public EducationBuilder description(String description) {
        this.description = description;
        return this;
    }

    public EducationBuilder grade(Double grade) {
        this.grade = grade;
        return this;
    }

    public EducationBuilder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public EducationBuilder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public EducationBuilder updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public EducationBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Education build() {
        return new Education(id, university, individual, fieldOfStudy, title, description, grade, startDate, endDate, updatedAt, createdAt);
    }

}
