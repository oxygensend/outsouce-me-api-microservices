package com.oxygensend.joboffer.context.job_offer.dto.command;

import com.oxygensend.joboffer.context.job_offer.dto.AddressDto;
import com.oxygensend.joboffer.context.job_offer.dto.SalaryRangeDto;
import com.oxygensend.joboffer.domain.entity.part.Experience;
import com.oxygensend.joboffer.domain.entity.part.FormOfEmployment;
import com.oxygensend.joboffer.domain.entity.part.WorkType;
import java.time.LocalDateTime;
import java.util.Set;

public record CreateJobOfferCommand(String name,
                                    String description,
                                    SalaryRangeDto salaryRange,
                                    FormOfEmployment formOfEmployment,
                                    AddressDto address,
                                    Set<String> technologies,
                                    Set<WorkType> workTypes,
                                    Experience experience,
                                    String principalId,
                                    LocalDateTime validTo) {
}
