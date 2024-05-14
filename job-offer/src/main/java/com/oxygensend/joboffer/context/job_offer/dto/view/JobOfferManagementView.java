package com.oxygensend.joboffer.context.job_offer.dto.view;

import com.oxygensend.joboffer.context.application.dto.view.ApplicationManagementView;
import com.oxygensend.joboffer.domain.entity.part.Experience;
import com.oxygensend.joboffer.domain.entity.part.FormOfEmployment;
import com.oxygensend.joboffer.domain.entity.part.WorkType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JobOfferManagementView extends BaseJobOfferView {

    public final String description;
    public final Set<WorkType> workTypes;
    public final Experience experience;
    public final FormOfEmployment formOfEmployment;
    public final List<String> technologies;
    public final int numberOfApplications;
    public final SalaryRangeView salaryRange;
    public final AddressView address;
    public final LocalDateTime createdAt;
    public final LocalDate validTo;
    public final int redirectCount;
    public final List<ApplicationManagementView> applications;
    public final boolean archived;

    public JobOfferManagementView(Long id, String slug, String name, String description, Set<WorkType> workTypes, Experience experience,
                                  FormOfEmployment formOfEmployment, List<String> technologies, int numberOfApplications, SalaryRangeView salaryRange,
                                  AddressView address, LocalDateTime createdAt, LocalDateTime validto, int redirectCount,
                                  List<ApplicationManagementView> applications, boolean archived) {
        super(id, slug, name);
        this.description = description;
        this.workTypes = workTypes;
        this.experience = experience;
        this.formOfEmployment = formOfEmployment;
        this.technologies = technologies;
        this.numberOfApplications = numberOfApplications;
        this.salaryRange = salaryRange;
        this.address = address;
        this.createdAt = createdAt;
        this.validTo = validto.toLocalDate();
        this.redirectCount = redirectCount;
        this.applications = applications;
        this.archived = archived;
    }
}
