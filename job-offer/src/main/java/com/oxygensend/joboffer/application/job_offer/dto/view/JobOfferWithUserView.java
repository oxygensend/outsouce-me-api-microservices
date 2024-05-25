package com.oxygensend.joboffer.application.job_offer.dto.view;

import com.oxygensend.joboffer.application.user.dto.view.BaseUserView;

public class JobOfferWithUserView extends BaseJobOfferView {
    public final BaseUserView user;


    public JobOfferWithUserView(Long id, String slug, String name, BaseUserView user) {
        super(id, slug, name);
        this.user = user;
    }
}
