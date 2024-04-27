package com.oxygensend.joboffer.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String originalName;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long size;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Application application;

    public Attachment() {
    }
    public Attachment(String originalName, String name, Long size, User createdBy) {
        this.originalName = originalName;
        this.name = name;
        this.size = size;
        this.createdBy = createdBy;
    }
    public Attachment(String originalName, String name, Long size, User createdBy, Application application) {
        this.originalName = originalName;
        this.name = name;
        this.size = size;
        this.createdBy = createdBy;
        this.application = application;
    }

    public Long id() {
        return id;
    }

    public String originalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long size() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public User createdBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Application application() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

}

