package com.oxygensend.joboffer.application.job_offer.dto.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oxygensend.joboffer.application.user.dto.view.BaseUserView;

public class JobOfferWithUserView extends BaseJobOfferView {
    public final BaseUserView user;


    @JsonCreator
    public JobOfferWithUserView(@JsonProperty("id") Long id, @JsonProperty("slug") String slug, @JsonProperty("name") String name,
                                @JsonProperty("user") BaseUserView user) {
        super(id, slug, name);
        this.user = user;
    }
}
