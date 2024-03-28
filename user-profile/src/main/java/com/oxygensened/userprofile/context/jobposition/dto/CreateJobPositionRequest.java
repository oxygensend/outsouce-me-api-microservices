package com.oxygensened.userprofile.context.jobposition.dto;

import com.oxygensened.userprofile.domain.FormOfEmployment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateJobPositionRequest(@NotEmpty String name,
                                       @NotNull FormOfEmployment formOfEmployment,
                                       @NotEmpty String companyName,
                                       String description,
                                       @NotNull LocalDate startDate,
                                       LocalDate endDate) {
}
