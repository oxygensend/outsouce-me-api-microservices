package com.oxygensened.userprofile.domain;

import com.oxygensened.userprofile.infrastructure.jpa.StringSetConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class User {

    @Id
    private UUID id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    private String phoneNumber;
    @Column(length = 1000)
    private String description;
    private String githubUrl;
    private String linkedinUrl;
    private LocalDate dateOfBirth;
    @Column(nullable = false)
    private Integer redirectCount;
    private AccountType accountType;
    @Column(nullable = false)
    private String slug;
    @Column(nullable = false)
    private boolean lookingForJob;
    private String activeJobPosition;
    @Column(nullable = false)
    private double opinionsRate = 0;
    private Experience experience;
    private double popularityOrder;
    private String imageName;
    private String imageNameSmall;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    @Convert(converter = StringSetConverter.class)
    private Set<String> technologies;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Address address;
    @OneToMany(mappedBy = "createdBy", targetEntity = Attachment.class)
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "toWho", targetEntity = Opinion.class, orphanRemoval = true)
    private List<Opinion> opinions = new ArrayList<>();

    @OneToMany(mappedBy = "individual", targetEntity = Education.class)
    private List<Education> educations = new ArrayList<>();

    @OneToMany(mappedBy = "individual", targetEntity = JobPosition.class)
    private List<JobPosition> jobPositions = new ArrayList<>();

    @OneToMany(mappedBy = "user", targetEntity = Language.class)
    private List<Language> languages = new ArrayList<>();

    public User() {
    }

    public User(UUID id, String email, String name, String surname, String phoneNumber, String description, String githubUrl, String linkedinUrl,
                LocalDate dateOfBirth, Integer redirectCount, AccountType accountType, String slug, boolean lookingForJob, String activeJobPosition,
                double opinionsRate, Experience experience, double popularityOrder, String imageName, String imageNameSmall, LocalDateTime createdAt,
                LocalDateTime updatedAt, Set<String> technologies, Address address, List<Attachment> attachments, List<Opinion> opinions,
                List<Education> educations, List<JobPosition> jobPositions, List<Language> languages) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
        this.dateOfBirth = dateOfBirth;
        this.redirectCount = redirectCount;
        this.accountType = accountType;
        this.slug = slug;
        this.lookingForJob = lookingForJob;
        this.activeJobPosition = activeJobPosition;
        this.opinionsRate = opinionsRate;
        this.experience = experience;
        this.popularityOrder = popularityOrder;
        this.imageName = imageName;
        this.imageNameSmall = imageNameSmall;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.technologies = technologies;
        this.address = address;
        this.attachments = attachments;
        this.opinions = opinions;
        this.educations = educations;
        this.jobPositions = jobPositions;
        this.languages = languages;
    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String email() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String phoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String githubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String linkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public LocalDate dateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer redirectCount() {
        return redirectCount;
    }

    public void setRedirectCount(Integer redirectCount) {
        this.redirectCount = redirectCount;
    }

    public AccountType accountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String slug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public boolean lookingForJob() {
        return lookingForJob;
    }

    public void setLookingForJob(boolean lookingForJob) {
        this.lookingForJob = lookingForJob;
    }

    public String activeJobPosition() {
        return activeJobPosition;
    }

    public void setActiveJobPosition(String activeJobPosition) {
        this.activeJobPosition = activeJobPosition;
    }

    public double opinionsRate() {
        return opinionsRate;
    }

    public void setOpinionsRate(double opinionsRate) {
        this.opinionsRate = opinionsRate;
    }

    public Experience experience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public double popularityOrder() {
        return popularityOrder;
    }

    public void setPopularityOrder(double popularityOrder) {
        this.popularityOrder = popularityOrder;
    }

    public String imageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String imageNameSmall() {
        return imageNameSmall;
    }

    public void setImageNameSmall(String imageNameSmall) {
        this.imageNameSmall = imageNameSmall;
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

    public Set<String> technologies() {
        return technologies;
    }

    public void setTechnologies(Set<String> technologies) {
        this.technologies = technologies;
    }

    public Address address() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Attachment> attachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<Opinion> opinions() {
        return opinions;
    }

    public void setOpinions(List<Opinion> opinions) {
        this.opinions = opinions;
    }

    public List<Education> educations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<JobPosition> jobPositions() {
        return jobPositions;
    }

    public void setJobPositions(List<JobPosition> jobPositions) {
        this.jobPositions = jobPositions;
    }

    public List<Language> languages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }
}
