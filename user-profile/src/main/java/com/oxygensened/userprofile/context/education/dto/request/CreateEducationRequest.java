package com.oxygensened.userprofile.context.education.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CreateEducationRequest(@NotNull Long universityId,
                                     @Size(min = 3, max = 64, message = "Field of study length must be between 3 and 64 characters")
                                     @NotEmpty String fieldOfStudy,
                                     @Size(min = 2, max = 128, message = "Title length must be between 3 and 128 characters")
                                     String title,
                                     Double grade,
                                     @Size(min = 3, max = 512, message = "Description length must be between 3 and 64 characters")
                                     String description,
                                     @NotNull LocalDate startDate,
                                     LocalDate endDate
) {

}
