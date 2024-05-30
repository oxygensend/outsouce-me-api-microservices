package com.oxygensend.joboffer.application.job_offer.dto.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oxygensend.joboffer.application.user.dto.view.BaseUserView;
import com.oxygensend.joboffer.domain.entity.part.Experience;
import com.oxygensend.joboffer.domain.entity.part.FormOfEmployment;
import com.oxygensend.joboffer.domain.entity.part.WorkType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JobOfferDetailsView extends JobOfferWithUserView {
    public final String description;
    public final Set<WorkType> workTypes;
    public final Experience experience;
    public final FormOfEmployment formOfEmployment;
    public final List<String> technologies;
    public final int numberOfApplications;
    public final SalaryRangeView salaryRange;
    public final AddressView address;
    public final LocalDateTime createdAt;
    public final LocalDateTime validTo;

    @JsonCreator
    public JobOfferDetailsView(@JsonProperty("id") Long id, @JsonProperty("slug") String slug, @JsonProperty("name") String name,
                               @JsonProperty("user") BaseUserView user, @JsonProperty("description") String description,
                               @JsonProperty("workTypes") Set<WorkType> workTypes, @JsonProperty("experience") Experience experience,
                               @JsonProperty("formOfEmployment") FormOfEmployment formOfEmployment, @JsonProperty("technologies") List<String> technologies,
                               @JsonProperty("numberOfApplications") int numberOfApplications, @JsonProperty("salaryRange") SalaryRangeView salaryRange,
                               @JsonProperty("address") AddressView address, @JsonProperty("createdAt") LocalDateTime createdAt,
                               @JsonProperty("validTo") LocalDateTime validTo) {
        super(id, slug, name, user);
        this.description = description;
        this.workTypes = workTypes;
        this.experience = experience;
        this.formOfEmployment = formOfEmployment;
        this.technologies = technologies;
        this.numberOfApplications = numberOfApplications;
        this.salaryRange = salaryRange;
        this.address = address;
        this.createdAt = createdAt;
        this.validTo = validTo;
    }


}
