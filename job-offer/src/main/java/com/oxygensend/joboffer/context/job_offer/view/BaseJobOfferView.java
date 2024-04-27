package com.oxygensend.joboffer.context.job_offer.view;

public class BaseJobOfferView {
    public final Long id;
    public final String slug;
    public final String name;

    public BaseJobOfferView(Long id, String slug, String name) {
        this.id = id;
        this.slug = slug;
        this.name = name;
    }
}
