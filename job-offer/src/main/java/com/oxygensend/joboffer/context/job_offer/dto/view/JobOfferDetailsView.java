package com.oxygensend.joboffer.context.job_offer.dto.view;

import com.oxygensend.joboffer.context.user.dto.view.BaseUserView;
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
    public final LocalDateTime validto;


    public JobOfferDetailsView(Long id, String slug, String name, BaseUserView user, String description, Set<WorkType> workTypes,
                               Experience experience, FormOfEmployment formOfEmployment, List<String> technologies, int numberOfApplications,
                               SalaryRangeView salaryRange, AddressView address, LocalDateTime createdAt, LocalDateTime validto) {
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
        this.validto = validto;
    }
}
