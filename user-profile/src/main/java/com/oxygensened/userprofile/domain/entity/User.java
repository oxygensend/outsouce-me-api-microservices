package com.oxygensened.userprofile.domain.entity;

import com.oxygensened.userprofile.domain.Slug;
import com.oxygensened.userprofile.domain.entity.part.AccountType;
import com.oxygensened.userprofile.domain.entity.part.Experience;
import com.oxygensened.userprofile.infrastructure.jpa.converter.AccountTypeConverter;
import com.oxygensened.userprofile.infrastructure.jpa.converter.ExperienceConverter;
import com.oxygensened.userprofile.infrastructure.jpa.converter.StringSetConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Table(indexes = {
        @Index(name = "email_idx", columnList = "email"),
        @Index(name = "slug_idx", columnList = "slug"),
        @Index(name = "externalId_idx", columnList = "external_id"),
        @Index(name = "popularityOrder_idx", columnList = "popularityOrder DESC"),
        @Index(name = "createdAt_idx", columnList = "createdAt DESC"),
        @Index(name = "accountType_lookingForJob_popular_idx", columnList = "accountType, lookingForJob, popularityOrder"),
        @Index(name = "accountType_lookingForJob_newest_idx", columnList = "accountType, lookingForJob, createdAt"),
})
@Entity
public class User implements Serializable {
    @Id
    private Long id;
    @Column(nullable = false)
    private String externalId;
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
    private Integer redirectCount = 0;
    @Convert(converter = AccountTypeConverter.class)
    private AccountType accountType;
    @Slug(source = {"name", "surname"})
    @Column(nullable = false, unique = true)
    private String slug;
    @Column(nullable = false)
    private boolean lookingForJob;
    private String activeJobPosition;
    @Convert(converter = ExperienceConverter.class)
    private Experience experience;
    private double popularityOrder;
    private String imageName;
    private String imageNameSmall;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    @Convert(converter = StringSetConverter.class)
    private Set<String> technologies = new HashSet<>();
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn
    private Address address;

    @OneToMany(mappedBy = "individual", targetEntity = Education.class)
    private List<Education> educations = new ArrayList<>();

    @OneToMany(mappedBy = "individual", targetEntity = JobPosition.class)
    private List<JobPosition> jobPositions = new ArrayList<>();

    @OneToMany(mappedBy = "user", targetEntity = Language.class)
    private List<Language> languages = new ArrayList<>();
    @Column(nullable = false)
    private double opinionsRate = 0;

    @Column(nullable = false)
    private int opinionsCount = 0;

    @Transient
    private Integer displayOrder;


    public User() {
    }

    public User(Long id, String externalId, String email, String name, String surname, String phoneNumber, String description, String githubUrl, String linkedinUrl,
                LocalDate dateOfBirth, Integer redirectCount, AccountType accountType, String slug, boolean lookingForJob, String activeJobPosition,
                double opinionsRate, Experience experience, double popularityOrder, String imageName, String imageNameSmall, LocalDateTime createdAt,
                LocalDateTime updatedAt, Set<String> technologies, Address address, List<Education> educations, List<JobPosition> jobPositions,
                List<Language> languages, int opinionsCount) {
        this.id = id;
        this.externalId = externalId;
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
        this.educations = educations;
        this.jobPositions = jobPositions;
        this.languages = languages;
        this.opinionsCount = opinionsCount;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public Long id() {
        return id;
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

    public String externalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }


    public String fullName() {
        return name + " " + surname;
    }

    public void addTechnology(String name) {
        technologies.add(name);
    }

    public void removeTechnology(String technology) {
        technologies.remove(technology);
    }

    public int opinionsCount() {
        return opinionsCount;
    }

    public void setOpinionsCount(int opinionsCount) {
        this.opinionsCount = opinionsCount;
    }


    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (externalId != null) {
            map.put("externalId", externalId);
        }
        if (email != null) {
            map.put("email", email);
        }
        if (name != null) {
            map.put("name", name);
        }
        if (surname != null) {
            map.put("surname", surname);
        }
        if (accountType != null) {
            map.put("accountType", accountType);
        }
        if (slug != null) {
            map.put("slug", slug);
        }
        if (address != null) {
            map.put("address", address);
        }
        if (experience != null) {
            map.put("experience", experience);
        }
        if (technologies != null) {
            map.put("technologies", technologies);
        }
        if (activeJobPosition != null) {
            map.put("activeJobPosition", activeJobPosition);
        }

        return map;
    }

    public Integer displayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

}
