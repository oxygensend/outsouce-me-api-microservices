package com.oxygensend.joboffer.domain.entity;

import com.oxygensend.joboffer.domain.Slug;
import com.oxygensend.joboffer.domain.entity.part.Experience;
import com.oxygensend.joboffer.domain.entity.part.FormOfEmployment;
import com.oxygensend.joboffer.domain.entity.part.WorkType;
import com.oxygensend.joboffer.infrastructure.jpa.converter.ExperienceConverter;
import com.oxygensend.joboffer.infrastructure.jpa.converter.StringSetConverter;
import com.oxygensend.joboffer.infrastructure.jpa.converter.WorkTypesConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(indexes = {
        @Index(name = "slug_idx", columnList = "slug", unique = true),
        @Index(name = "archived_idx", columnList = "archived"),
        @Index(name = "popularity_idx", columnList = "popularityOrder DESC"),
        @Index(name = "createdAt_idx", columnList = "createdAt DESC"),
        @Index(name = "archived_user_popularity_idx", columnList = "archived,user_id,popularityOrder DESC"),
        @Index(name = "archived_createdAt_idx", columnList = "archived,createdAt DESC"),
        @Index(name = "valid_to_idx", columnList = "valid_to"),
})
@Entity
public class JobOffer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Slug
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 1028)
    private String description;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Address address;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private FormOfEmployment formOfEmployment;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private SalaryRange salaryRange;
    @Convert(converter = ExperienceConverter.class)
    @Column(length = 1)
    private Experience experience;
    @Column(nullable = false, unique = true)
    private String slug;
    @Column(nullable = false)
    private int numberOfApplications = 0;
    @Column(nullable = false)
    private int redirectCount = 0;
    private Integer popularityOrder;
    private Integer displayOrder;
    @Column(nullable = false)
    private boolean archived = false;
    @Convert(converter = StringSetConverter.class) private Set<String> technologies = new HashSet<>();
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "jobOffer", fetch = FetchType.LAZY)
    private List<Application> applications = new ArrayList<>();
    @Convert(converter = WorkTypesConverter.class)
    private Set<WorkType> workTypes = new HashSet<>();
    private LocalDateTime validTo;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt = LocalDateTime.now();

    public JobOffer() {

    }

    JobOffer(Long id, String name, String description, Address address, User user, FormOfEmployment formOfEmployment, SalaryRange salaryRange,
             Experience experience, String slug, int numberOfApplications, int redirectCount, Integer popularityOrder, Integer displayOrder,
             boolean archived, Set<String> technologies, List<Application> applications, Set<WorkType> workTypes, LocalDateTime validTo,
             LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.user = user;
        this.formOfEmployment = formOfEmployment;
        this.salaryRange = salaryRange;
        this.experience = experience;
        this.slug = slug;
        this.numberOfApplications = numberOfApplications;
        this.redirectCount = redirectCount;
        this.popularityOrder = popularityOrder;
        this.displayOrder = displayOrder;
        this.archived = archived;
        this.technologies = technologies = technologies == null ? new HashSet<>() : technologies;
        this.applications = applications;
        this.workTypes = workTypes == null ? new HashSet<>() : workTypes;
        this.validTo = validTo;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public static JobOfferBuilder builder() {
        return new JobOfferBuilder();
    }

    public Long id() {
        return id;
    }


    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String description() {
        return description;
    }

    public String shortDescription() {
        var endIndex = Math.min(description.length(), 100);
        return description.substring(0, endIndex);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address address() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User user() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FormOfEmployment formOfEmployment() {
        return formOfEmployment;
    }

    public void setFormOfEmployment(FormOfEmployment formOfEmployment) {
        this.formOfEmployment = formOfEmployment;
    }

    public SalaryRange salaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(SalaryRange salaryRange) {
        this.salaryRange = salaryRange;
    }

    public Experience experience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public String slug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int numberOfApplications() {
        return numberOfApplications;
    }

    public void setNumberOfApplications(int numberOfApplications) {
        this.numberOfApplications = numberOfApplications;
    }

    public void increaseNumberOfApplications() {
        numberOfApplications++;
    }

    public int redirectCount() {
        return redirectCount;
    }

    public void setRedirectCount(int redirectCount) {
        this.redirectCount = redirectCount;
    }

    public Integer popularityOrder() {
        return popularityOrder;
    }

    public void setPopularityOrder(Integer popularityOrder) {
        this.popularityOrder = popularityOrder;
    }

    public Integer displayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean archived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Set<String> technologies() {
        return technologies;
    }

    public void setTechnologies(Set<String> technologies) {
        this.technologies = technologies;
    }

    public List<Application> applications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public Set<WorkType> workTypes() {
        return workTypes;
    }

    public void setWorkTypes(Set<WorkType> workTypes) {
        this.workTypes = workTypes;
    }

    public LocalDateTime validTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
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

    public void addRedirect() {
        this.redirectCount++;
    }
}
