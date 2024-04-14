package com.oxygensend.joboffer.context.job_offer.dto;

import com.oxygensend.joboffer.domain.SalaryType;
import com.oxygensend.joboffer.domain.SupportedCurrency;
import com.oxygensend.joboffer.domain.WorkType;
import com.oxygensend.joboffer.domain.entity.SalaryRange;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Currency;

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
