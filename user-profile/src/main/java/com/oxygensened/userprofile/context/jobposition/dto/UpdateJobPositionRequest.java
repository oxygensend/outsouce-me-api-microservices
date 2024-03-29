package com.oxygensened.userprofile.context.jobposition.dto;

import com.oxygensened.userprofile.domain.FormOfEmployment;
import java.time.LocalDate;
import org.openapitools.jackson.nullable.JsonNullable;

public record UpdateJobPositionRequest(JsonNullable<String> name,
                                       JsonNullable<String> description,
                                       JsonNullable<FormOfEmployment> formOfEmployment,
                                       JsonNullable<String> companyName,
                                       JsonNullable<LocalDate> startDate,
                                       JsonNullable<LocalDate> endDate) {
}
