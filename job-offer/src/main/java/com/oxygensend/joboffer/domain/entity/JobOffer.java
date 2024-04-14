package com.oxygensend.joboffer.domain.entity;

import com.oxygensend.joboffer.domain.Experience;
import com.oxygensend.joboffer.domain.FormOfEmployment;
import com.oxygensend.joboffer.domain.Slug;
import com.oxygensend.joboffer.domain.WorkType;
import com.oxygensend.joboffer.infrastructure.jpa.StringSetConverter;
import com.oxygensend.joboffer.infrastructure.jpa.WorkTypesConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Slug
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, length = 1028)
    private String description;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Address address;
    @ManyToOne
    private User user;
    @Column(nullable = false)
    private FormOfEmployment formOfEmployment;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private SalaryRange salaryRange;
    private Experience experience;
    @Column(nullable = false)
    private String slug;
    @Column(nullable = false)
    private int numberOfApplications = 0;
    @Column(nullable = false)
    private int redirectCount = 0;
    private Integer popularityOrder;
    private Integer displayOrder;
    @Column(nullable = false)
    private boolean archived = false;
    @Convert(converter = StringSetConverter.class)
    private Set<String> technologies = new HashSet<>();
    @OneToMany(cascade = CascadeType.REMOVE)
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
        return description.substring(0, 100);
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
