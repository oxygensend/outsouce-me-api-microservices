package com.oxygensend.joboffer.application.job_offer.dto.view;

import com.oxygensend.joboffer.domain.entity.SalaryRange;
import com.oxygensend.joboffer.domain.entity.part.SupportedCurrency;

public record SalaryRangeView(Double downRange,
                              Double upRange,
                              SupportedCurrency currency,
                              String type) {

    public static SalaryRangeView from(SalaryRange salaryRange) {
        return new SalaryRangeView(salaryRange.downRange(), salaryRange.upRange(), salaryRange.currency(), salaryRange.type().displayName());
    }
}
