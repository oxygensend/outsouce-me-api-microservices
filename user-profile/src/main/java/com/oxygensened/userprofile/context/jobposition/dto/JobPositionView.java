package com.oxygensened.userprofile.context.jobposition.dto;

import com.oxygensened.userprofile.context.company.CompanyView;
import com.oxygensened.userprofile.domain.FormOfEmployment;
import com.oxygensened.userprofile.domain.JobPosition;
import java.time.LocalDate;

public record JobPositionView(Long id,
                              String name,
                              String description,
                              FormOfEmployment formOfEmployment,
                              CompanyView company,
                              LocalDate startDate,
                              LocalDate endDate) {


    public static JobPositionView from(JobPosition jobPosition) {
        return new JobPositionView(jobPosition.id(),
                                   jobPosition.name(),
                                   jobPosition.description(),
                                   jobPosition.formOfEmployment(),
                                   CompanyView.from(jobPosition.company()),
                                   jobPosition.startDate(),
                                   jobPosition.endDate());
    }
}
