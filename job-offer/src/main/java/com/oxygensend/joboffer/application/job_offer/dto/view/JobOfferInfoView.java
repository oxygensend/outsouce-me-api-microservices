package com.oxygensend.joboffer.application.job_offer.dto.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class JobOfferInfoView extends BaseJobOfferView {
    public final int numberOfApplications;
    public final LocalDate validTo;
    public final boolean archived;

    @JsonCreator
    public JobOfferInfoView(@JsonProperty("id") Long id, @JsonProperty("slug") String slug, @JsonProperty("name") String name, @JsonProperty("numberOfApplications") int numberOfApplications, @JsonProperty("validTo") LocalDate validTo, @JsonProperty("archived") boolean archived) {
        super(id, slug, name);
        this.numberOfApplications = numberOfApplications;
        this.validTo = validTo;
        this.archived = archived;
    }

}
