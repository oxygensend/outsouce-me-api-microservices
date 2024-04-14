package com.oxygensend.joboffer.context.job_offer.dto;

import com.oxygensend.joboffer.domain.Experience;
import com.oxygensend.joboffer.domain.FormOfEmployment;
import com.oxygensend.joboffer.domain.WorkType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

public record CreateJobOfferRequest(@NotEmpty
                                    String name,
                                    @NotEmpty
                                    String description,
                                    @Valid
                                    SalaryRangeDto salaryRange,
                                    @NotNull
                                    FormOfEmployment formOfEmployment,
                                    @Valid
                                    AddressDto address,
                                    Set<String> technologies,
                                    Set<WorkType> workTypes,
                                    Experience experience,
                                    @NotEmpty
                                    String principalId,
                                    LocalDateTime validTo) {
}
