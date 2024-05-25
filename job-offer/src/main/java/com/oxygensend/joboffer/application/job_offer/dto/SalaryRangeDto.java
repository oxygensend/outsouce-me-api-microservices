package com.oxygensend.joboffer.application.job_offer.dto;

import com.oxygensend.joboffer.domain.entity.part.SalaryType;
import com.oxygensend.joboffer.domain.entity.part.SupportedCurrency;
import com.oxygensend.joboffer.domain.entity.SalaryRange;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SalaryRangeDto(@NotNull
                             @Positive
                             Double downRange,
                             @Positive
                             Double upRange,
                             SupportedCurrency currency,
                             SalaryType type) {


    public SalaryRange toSalaryRange(){
        return new SalaryRange(downRange, upRange, currency, type);
    }
}
