package com.oxygensend.joboffer.context.job_offer.dto.view;

import com.oxygensend.joboffer.context.user.dto.view.BaseUserView;

public class JobOfferWithUserView extends BaseJobOfferView {
    public final BaseUserView user;


    public JobOfferWithUserView(Long id, String slug, String name, BaseUserView user) {
        super(id, slug, name);
        this.user = user;
    }
}
