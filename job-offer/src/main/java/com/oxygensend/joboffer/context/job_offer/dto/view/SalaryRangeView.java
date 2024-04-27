package com.oxygensend.joboffer.context.job_offer.dto.view;

import com.oxygensend.joboffer.domain.entity.part.SalaryType;
import com.oxygensend.joboffer.domain.entity.part.SupportedCurrency;
import com.oxygensend.joboffer.domain.entity.SalaryRange;

public record SalaryRangeView(Double downRange,
                              Double upRange,
                              SupportedCurrency currency,
                              SalaryType type) {

    public static SalaryRangeView from(SalaryRange salaryRange) {
        return new SalaryRangeView(salaryRange.downRange(), salaryRange.upRange(), salaryRange.currency(), salaryRange.type());
    }
}
