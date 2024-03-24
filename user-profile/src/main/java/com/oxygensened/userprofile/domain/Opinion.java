package com.oxygensened.userprofile.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class Opinion {

    @Id
    private UUID id;
    @Column(length = 1000)
    private String description;
    @Column(nullable = false)
    private int scale;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User fromWho;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User toWho;

    public Opinion() {
    }

    public Opinion(UUID id, String description, int scale, User fromWho, User toWho) {
        this.id = id;
        this.description = description;
        this.scale = scale;
        this.fromWho = fromWho;
        this.toWho = toWho;
    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int scale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public User fromWho() {
        return fromWho;
    }

    public void setFromWho(User fromWho) {
        this.fromWho = fromWho;
    }

    public User toWho() {
        return toWho;
    }

    public void setToWho(User toWho) {
        this.toWho = toWho;
    }
}
