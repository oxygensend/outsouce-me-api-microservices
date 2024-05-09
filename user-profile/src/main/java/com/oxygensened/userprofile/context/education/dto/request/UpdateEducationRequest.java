package com.oxygensened.userprofile.context.education.dto.request;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import org.openapitools.jackson.nullable.JsonNullable;

public record UpdateEducationRequest(JsonNullable<Long> universityId,
                                     @Size(min = 3, max = 64, message = "Field of study length must be between 3 and 64 characters")
                                     JsonNullable<String> fieldOfStudy,
                                     @Size(min = 2, max = 128, message = "Title length must be between 3 and 128 characters")
                                     JsonNullable<String> title,
                                     JsonNullable<Double> grade,
                                     @Size(min = 3, max = 512, message = "Description length must be between 3 and 512 characters")
                                     JsonNullable<String> description,
                                     JsonNullable<LocalDate> startDate,
                                     JsonNullable<LocalDate> endDate) {
}
