package com.oxygensend.joboffer.context.job_offer.dto.view;

public class PrincipleJobOfferView extends BaseJobOfferView{
    public final String description;
    public final String shortDescription;
    public final int numberOfApplications;

    public PrincipleJobOfferView(Long id, String slug, String name, String description, String shortDescription, int numberOfApplications) {
        super(id, slug, name);
        this.description = description;
        this.shortDescription = shortDescription;
        this.numberOfApplications = numberOfApplications;
    }
}
