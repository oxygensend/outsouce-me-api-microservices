package com.oxygensened.userprofile.context.education.dto;

import java.time.LocalDate;
import org.openapitools.jackson.nullable.JsonNullable;

public record UpdateEducationRequest(JsonNullable<Long> universityId,
                                     JsonNullable<String> fieldOfStudy,
                                     JsonNullable<String> title,
                                     JsonNullable<Double> grade,
                                     JsonNullable<String> description,
                                     JsonNullable<LocalDate> startDate,
                                     JsonNullable<LocalDate> endDate) {
}
