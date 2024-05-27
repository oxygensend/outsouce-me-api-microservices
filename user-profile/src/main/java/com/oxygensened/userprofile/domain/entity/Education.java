package com.oxygensened.userprofile.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(indexes =  {
        @Index(name = "id_individualId_idx", columnList = "id,individual_id"),
        @Index(name = "individualId_startDate_idx", columnList = "individual_id,start_date DESC"),
})
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private University university;
    @ManyToOne
    private User individual;
    @Column(nullable = false)
    private String fieldOfStudy;
    private String title;
    @Column(length = 1000)
    private String description;
    private Double grade;
    @Column(nullable = false)
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Education() {
    }

    public Education(Long id, University university, User individual, String fieldOfStudy, String title, String description, Double grade,
                     LocalDate startDate, LocalDate endDate, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.id = id;
        this.university = university;
        this.individual = individual;
        this.fieldOfStudy = fieldOfStudy;
        this.title = title;
        this.description = description;
        this.grade = grade;
        this.startDate = startDate;
        this.endDate = endDate;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public static EducationBuilder builder() {
        return new EducationBuilder();
    }

    public Long id() {
        return id;
    }

    public University university() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public User individual() {
        return individual;
    }

    public void setIndividual(User individual) {
        this.individual = individual;
    }

    public String fieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String title() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double grade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
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

    public LocalDateTime updatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
