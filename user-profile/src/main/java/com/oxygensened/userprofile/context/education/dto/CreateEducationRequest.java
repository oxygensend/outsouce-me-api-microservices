package com.oxygensened.userprofile.context.education.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateEducationRequest(@NotNull Long universityId,
                                     @NotEmpty String fieldOfStudy,
                                     String title,
                                     Double grade,
                                     String description,
                                     @NotNull LocalDate startDate,
                                     LocalDate endDate
) {

}
