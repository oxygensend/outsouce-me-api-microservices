package com.oxygensend.joboffer.context.job_offer.dto.view;

import com.oxygensend.joboffer.context.user.dto.view.UserView;

public class JobOfferView extends BaseJobOfferView {
    public final String description;
    public final String shortDescription;
    public final int numberOfApplications;

    public final UserView user;
    public final boolean archived;


    public JobOfferView(Long id, String slug, String name, String description, String shortDescription, int numberOfApplications, UserView user, boolean archived) {
        super(id, slug, name);
        this.description = description;
        this.shortDescription = shortDescription;
        this.numberOfApplications = numberOfApplications;
        this.user = user;
        this.archived = archived;
    }
}
