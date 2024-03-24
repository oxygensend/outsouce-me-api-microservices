package com.oxygensened.userprofile.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class Attachment {

    @Id
    private UUID id;
    @Column(nullable = false)
    private String originalName;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String size;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User createdBy;

    public Attachment() {
    }

    public Attachment(UUID id, String originalName, String name, String size, User createdBy) {
        this.id = id;
        this.originalName = originalName;
        this.name = name;
        this.size = size;
        this.createdBy = createdBy;
    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String size() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public User createdBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
