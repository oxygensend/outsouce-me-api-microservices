package com.oxygensend.joboffer.context.job_offer.dto.request;

import com.oxygensend.joboffer.context.job_offer.dto.AddressDto;
import com.oxygensend.joboffer.domain.entity.part.Experience;
import com.oxygensend.joboffer.domain.entity.part.FormOfEmployment;
import com.oxygensend.joboffer.domain.entity.part.SalaryType;
import com.oxygensend.joboffer.domain.entity.part.SupportedCurrency;
import com.oxygensend.joboffer.domain.entity.part.WorkType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Set;
import org.openapitools.jackson.nullable.JsonNullable;

public record UpdateJobOfferRequest(@NotBlank
                                    String name,
                                    @NotBlank
                                    String description,
                                    @Valid
                                    JsonNullable<SalaryRangeDto> salaryRange,
                                    FormOfEmployment formOfEmployment,
                                    @Valid
                                    JsonNullable<AddressDto> address,
                                    Set<String> technologies,
                                    JsonNullable<Experience> experience,
                                    JsonNullable<Set<WorkType>> workTypes,
                                    JsonNullable<LocalDate> validTo) {


    public record SalaryRangeDto(Double downRange,
                                 @Positive
                                 JsonNullable<Double> upRange,
                                 SupportedCurrency currency,
                                 SalaryType type) {

    }
}
