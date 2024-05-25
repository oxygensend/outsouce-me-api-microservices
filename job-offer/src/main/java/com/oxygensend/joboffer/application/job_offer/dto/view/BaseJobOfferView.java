package com.oxygensend.joboffer.application.job_offer.dto.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseJobOfferView {
    public final Long id;
    public final String slug;
    public final String name;

    @JsonCreator
    public BaseJobOfferView(@JsonProperty("id") Long id, @JsonProperty("slug") String slug, @JsonProperty("name") String name) {
        this.id = id;
        this.slug = slug;
        this.name = name;
    }
}
