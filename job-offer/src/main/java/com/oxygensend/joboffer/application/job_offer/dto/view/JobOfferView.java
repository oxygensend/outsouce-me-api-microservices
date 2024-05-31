package com.oxygensend.joboffer.application.job_offer.dto.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oxygensend.joboffer.application.user.dto.view.UserView;

public class JobOfferView extends BaseJobOfferView {
    public final String description;
    public final String shortDescription;
    public final int numberOfApplications;

    public final UserView user;
    public final boolean archived;


    @JsonCreator
    public JobOfferView(@JsonProperty("id") Long id,
                        @JsonProperty("slug") String slug,
                        @JsonProperty("name") String name,
                        @JsonProperty("description") String description,
                        @JsonProperty("shortDescription") String shortDescription,
                        @JsonProperty("numberOfApplications") int numberOfApplications,
                        @JsonProperty("user") UserView user,
                        @JsonProperty("archived") boolean archived) {
        super(id, slug, name);
        this.description = description;
        this.shortDescription = shortDescription;
        this.numberOfApplications = numberOfApplications;
        this.user = user;
        this.archived = archived;
    }
}
