package com.oxygensend.joboffer.domain.entity;

import com.oxygensend.joboffer.domain.AccountType;
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
    private String thumbnail;
    private String activeJobPosition;
    private AccountType accountType;

    public User() {
    }

    public User(String id, String name, String surname, String email, String thumbnail, String activeJobPosition, AccountType accountType) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.thumbnail = thumbnail;
        this.activeJobPosition = activeJobPosition;
        this.accountType = accountType;
    }

    public String id() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String surname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String fullName() {
        return name + " " + surname;
    }

    public String email() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String thumbnail() {
        return thumbnail;
    }

    public String thumbnailPath(String serverUrl) {
        return thumbnail != null ? serverUrl + "/" + thumbnail : null;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String activeJobPosition() {
        return activeJobPosition;
    }

    public void setActiveJobPosition(String activeJobPosition) {
        this.activeJobPosition = activeJobPosition;
    }

    public AccountType accountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean canApplyForJobOffers() {
        return accountType == AccountType.DEVELOPER;
    }

    public boolean canPublishJobOffers() {
        return accountType == AccountType.PRINCIPLE;
    }
}
