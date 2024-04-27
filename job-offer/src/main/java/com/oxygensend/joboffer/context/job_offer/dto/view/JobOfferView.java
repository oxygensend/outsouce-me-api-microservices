package com.oxygensend.joboffer.context.job_offer.dto.view;

import com.oxygensend.joboffer.context.user.dto.view.UserView;

public class JobOfferView extends BaseJobOfferView {
    public final String description;
    public final String shortDescription;
    public final UserView user;

    public JobOfferView(Long id, String slug, String name, String description, String shortDescription, UserView user) {
        super(id, slug, name);
        this.description = description;
        this.shortDescription = shortDescription;
        this.user = user;
    }
}
