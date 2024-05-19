package com.oxygensened.userprofile.domain.entity;

import com.oxygensened.userprofile.domain.entity.part.AccountType;
import com.oxygensened.userprofile.domain.entity.part.Experience;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class UserBuilder {
    private Long id;
    private String externalId;
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;
    private String description;
    private String githubUrl;
    private String linkedinUrl;
    private LocalDate dateOfBirth;
    private Integer redirectCount = 0;
    private AccountType accountType;
    private String slug;
    private boolean lookingForJob;
    private String activeJobPosition;
    private Experience experience;
    private double popularityOrder;
    private String imageName;
    private String imageNameSmall;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private Set<String> technologies = Set.of();
    private Address address;
    private List<Education> educations = List.of();
    private List<JobPosition> jobPositions = List.of();
    private List<Language> languages = List.of();

    private List<JobOffer> jobOffers = List.of();

    private double opinionsRate = 0;
    private int opinionsCount = 0;

    public UserBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder externalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder surname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserBuilder description(String description) {
        this.description = description;
        return this;
    }

    public UserBuilder githubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
        return this;
    }

    public UserBuilder linkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
        return this;
    }

    public UserBuilder dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public UserBuilder redirectCount(Integer redirectCount) {
        this.redirectCount = redirectCount;
        return this;
    }

    public UserBuilder accountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public UserBuilder slug(String slug) {
        this.slug = slug;
        return this;
    }

    public UserBuilder lookingForJob(boolean lookingForJob) {
        this.lookingForJob = lookingForJob;
        return this;
    }

    public UserBuilder activeJobPosition(String activeJobPosition) {
        this.activeJobPosition = activeJobPosition;
        return this;
    }

    public UserBuilder opinionsRate(double opinionsRate) {
        this.opinionsRate = opinionsRate;
        return this;
    }

    public UserBuilder experience(Experience experience) {
        this.experience = experience;
        return this;
    }

    public UserBuilder popularityOrder(double popularityOrder) {
        this.popularityOrder = popularityOrder;
        return this;
    }

    public UserBuilder imageName(String imageName) {
        this.imageName = imageName;
        return this;
    }

    public UserBuilder imageNameSmall(String imageNameSmall) {
        this.imageNameSmall = imageNameSmall;
        return this;
    }

    public UserBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserBuilder updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public UserBuilder technologies(Set<String> technologies) {
        this.technologies = technologies;
        return this;
    }

    public UserBuilder address(Address address) {
        this.address = address;
        return this;
    }


    public UserBuilder educations(List<Education> educations) {
        this.educations = educations;
        return this;
    }

    public UserBuilder jobPositions(List<JobPosition> jobPositions) {
        this.jobPositions = jobPositions;
        return this;
    }

    public UserBuilder languages(List<Language> languages) {
        this.languages = languages;
        return this;
    }

    public UserBuilder opinionsCount(int opinionsCount) {
        this.opinionsCount = opinionsCount;
        return this;
    }

    public UserBuilder jobOffers(List<JobOffer> jobOffers) {
        this.jobOffers = jobOffers;
        return this;
    }

    public User build() {
        return new User(id, externalId, email, name, surname, phoneNumber, description, githubUrl, linkedinUrl, dateOfBirth, redirectCount, accountType, slug, lookingForJob, activeJobPosition, opinionsRate, experience, popularityOrder, imageName, imageNameSmall, createdAt, updatedAt, technologies, address, educations, jobPositions, languages, opinionsCount, jobOffers);
    }

}
