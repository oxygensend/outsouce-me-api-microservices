package com.oxygensend.joboffer.application.job_offer.dto.view;

import java.time.LocalDate;

public class JobOfferInfoView extends BaseJobOfferView {
    public final int numberOfApplications;
    public final LocalDate validTo;
    public final boolean archived;
    public JobOfferInfoView(Long id, String slug, String name, String description, String shortDescription, int numberOfApplications, LocalDate validTo, boolean archived) {
        super(id, slug, name);
        this.numberOfApplications = numberOfApplications;
        this.validTo = validTo;
        this.archived = archived;
    }
}
