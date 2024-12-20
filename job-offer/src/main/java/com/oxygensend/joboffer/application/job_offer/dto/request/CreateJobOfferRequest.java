package com.oxygensend.joboffer.application.job_offer.dto.request;

import com.oxygensend.joboffer.application.job_offer.dto.AddressDto;
import com.oxygensend.joboffer.application.job_offer.dto.SalaryRangeDto;
import com.oxygensend.joboffer.application.job_offer.dto.command.CreateJobOfferCommand;
import com.oxygensend.joboffer.domain.entity.part.Experience;
import com.oxygensend.joboffer.domain.entity.part.FormOfEmployment;
import com.oxygensend.joboffer.domain.entity.part.WorkType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

public record CreateJobOfferRequest(@NotEmpty
                                    String name,
                                    @NotEmpty
                                    String description,
                                    @Valid
                                    SalaryRangeDto salaryRange,
                                    @NotNull
                                    FormOfEmployment formOfEmployment,
                                    @Valid
                                    AddressDto address,
                                    Set<String> technologies,
                                    Set<WorkType> workTypes,
                                    Experience experience,
                                    @NotEmpty
                                    String principalId,
                                    LocalDate validTo) {


    public CreateJobOfferCommand toCommand() {
        return new CreateJobOfferCommand(name, description, salaryRange, formOfEmployment, address, technologies, workTypes, experience,
                                         principalId, validTo);
    }
}
