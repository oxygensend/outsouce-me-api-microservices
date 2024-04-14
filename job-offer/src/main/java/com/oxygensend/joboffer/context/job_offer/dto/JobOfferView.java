package com.oxygensend.joboffer.context.job_offer.dto;

import com.oxygensend.joboffer.domain.Experience;
import com.oxygensend.joboffer.domain.FormOfEmployment;
import com.oxygensend.joboffer.domain.WorkType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record JobOfferView(Long id,
                           String slug,
                           String name,
                           String description,
                           Set<WorkType> workTypes,
                           Experience experience,
                           FormOfEmployment formOfEmployment,
                           List<String> technologies,
                           int numberOfApplications,
                           SalaryRangeView salaryRange,
                           AddressView address,
                           UserView user,
                           LocalDateTime createdAt

) {

}
