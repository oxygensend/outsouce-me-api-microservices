package com.oxygensend.joboffer.infrastructure.elasticsearch;

import com.oxygensend.joboffer.domain.JobOfferSearchResult;
import com.oxygensend.joboffer.domain.entity.JobOffer;

public class ElasticSearchMapper {
    public JobOfferES mapJobOfferToJobOfferES(JobOffer jobOffer) {
        var city = jobOffer.address() != null ? jobOffer.address().city() : null;
        return new JobOfferES(jobOffer.id().toString(), jobOffer.name(), jobOffer.slug(), jobOffer.description(), city,
                              jobOffer.user().fullName(), jobOffer.technologies(), jobOffer.popularityOrder(), jobOffer.numberOfApplications());
    }

    public JobOfferSearchResult mapJobOfferESToJobOfferSearchResult(JobOfferES jobOfferES) {
        return new JobOfferSearchResult(jobOfferES.id(), jobOfferES.name(), jobOfferES.slug(), jobOfferES.description(), jobOfferES.numberOfApplications());
    }
}
