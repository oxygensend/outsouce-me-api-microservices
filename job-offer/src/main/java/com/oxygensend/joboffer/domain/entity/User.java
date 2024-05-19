package com.oxygensend.joboffer.domain.entity;

import com.oxygensend.joboffer.domain.entity.part.AccountType;
import com.oxygensend.joboffer.domain.entity.part.Experience;
import com.oxygensend.joboffer.infrastructure.jpa.converter.ExperienceConverter;
import com.oxygensend.joboffer.infrastructure.jpa.converter.StringSetConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements Serializable {
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
    @Column(nullable = false)
    private int opinionsCount = 0;
    @Column(nullable = false)
    private double opinionsRate = 0;
    private Double latitude;
    private Double longitude;

    @Convert(converter = ExperienceConverter.class)
    private Experience experience;
    @Convert(converter = StringSetConverter.class)
    private Set<String> technologies = new HashSet<>();


    public User() {
    }

    public User(String id, String name, String surname, String email, String thumbnail, String activeJobPosition, AccountType accountType,
                Double longitude, Double latitude) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.thumbnail = thumbnail;
        this.activeJobPosition = activeJobPosition;
        this.accountType = accountType;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public User(String id, String name, String surname, String email, String thumbnail, String activeJobPosition, AccountType accountType,
                Double longitude, Double latitude, Experience experience, Set<String> technologies) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.thumbnail = thumbnail;
        this.activeJobPosition = activeJobPosition;
        this.accountType = accountType;
        this.longitude = longitude;
        this.latitude = latitude;
        this.experience = experience;
        this.technologies = technologies;
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

    public int opinionsCount() {
        return opinionsCount;
    }

    public void setOpinionsCount(int opinionsCount) {
        this.opinionsCount = opinionsCount;
    }

    public double opinionsRate() {
        return opinionsRate;
    }

    public void setOpinionsRate(double opinionsRate) {
        this.opinionsRate = opinionsRate;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double latitude() {
        return latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double longitude() {
        return longitude;
    }

    public Experience experience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public Set<String> technologies() {
        return technologies;
    }

    public void setTechnologies(Set<String> technologies) {
        this.technologies = technologies;
    }
}
