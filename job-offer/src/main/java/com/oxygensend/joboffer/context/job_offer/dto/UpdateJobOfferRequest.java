package com.oxygensend.joboffer.context.job_offer.dto;

import com.oxygensend.joboffer.domain.Experience;
import com.oxygensend.joboffer.domain.FormOfEmployment;
import com.oxygensend.joboffer.domain.SalaryType;
import com.oxygensend.joboffer.domain.SupportedCurrency;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
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
                                    JsonNullable<LocalDateTime> validTo) {


    public record SalaryRangeDto(Double downRange,
                                 @Positive
                                 JsonNullable<Double> upRange,
                                 SupportedCurrency currency,
                                 SalaryType type) {

    }
}
