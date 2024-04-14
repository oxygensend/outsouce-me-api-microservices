package com.oxygensend.joboffer.domain.entity;

import com.oxygensend.joboffer.domain.Experience;
import com.oxygensend.joboffer.domain.FormOfEmployment;
import com.oxygensend.joboffer.domain.WorkType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JobOfferBuilder {

    private Long id;
    private String name;
    private String description;
    private Address address;
    private User user;
    private FormOfEmployment formOfEmployment;
    private SalaryRange salaryRange;
    private Experience experience;
    private String slug;
    private int numberOfApplications = 0;
    private int redirectCount = 0;
    private Integer popularityOrder;
    private Integer displayOrder;
    private boolean archived = false;
    private Set<String> technologies = new HashSet<>();
    private List<Application> applications = new ArrayList<>();
    private List<WorkType> workTypes = new ArrayList<>();
    private LocalDateTime validTo;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt = LocalDateTime.now();


    public JobOfferBuilder() {
    }

    public JobOfferBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public JobOfferBuilder name(String name) {
        this.name = name;
        return this;
    }

    public JobOfferBuilder description(String description) {
        this.description = description;
        return this;
    }

    public JobOfferBuilder address(Address address) {
        this.address = address;
        return this;
    }

    public JobOfferBuilder user(User user) {
        this.user = user;
        return this;
    }

    public JobOfferBuilder formOfEmployment(FormOfEmployment formOfEmployment) {
        this.formOfEmployment = formOfEmployment;
        return this;
    }

    public JobOfferBuilder salaryRange(SalaryRange salaryRange) {
        this.salaryRange = salaryRange;
        return this;
    }

    public JobOfferBuilder experience(Experience experience) {
        this.experience = experience;
        return this;
    }

    public JobOfferBuilder slug(String slug) {
        this.slug = slug;
        return this;
    }

    public JobOfferBuilder numberOfApplications(int numberOfApplications) {
        this.numberOfApplications = numberOfApplications;
        return this;
    }

    public JobOfferBuilder redirectCount(int redirectCount) {
        this.redirectCount = redirectCount;
        return this;
    }

    public JobOfferBuilder popularityOrder(Integer popularityOrder) {
        this.popularityOrder = popularityOrder;
        return this;
    }

    public JobOfferBuilder displayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public JobOfferBuilder archived(boolean archived) {
        this.archived = archived;
        return this;
    }

    public JobOfferBuilder technologies(Set<String> technologies) {
        this.technologies = technologies;
        return this;
    }

    public JobOfferBuilder applications(List<Application> applications) {
        this.applications = applications;
        return this;
    }

    public JobOfferBuilder workTypes(List<WorkType> workTypes) {
        this.workTypes = workTypes;
        return this;
    }

    public JobOfferBuilder validTo(LocalDateTime validTo) {
        this.validTo = validTo;
        return this;
    }

    public JobOfferBuilder updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public JobOfferBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public JobOffer build() {
        return new JobOffer(id, name, description, address, user, formOfEmployment, salaryRange, experience, slug, numberOfApplications,
                            redirectCount, popularityOrder, displayOrder, archived, technologies, applications, workTypes, validTo, updatedAt,
                            createdAt);
    }

}
