package com.oxygensend.joboffer.application;

import com.oxygensend.joboffer.domain.entity.Application;
import com.oxygensend.joboffer.domain.entity.JobOffer;

public interface ApplicationCacheManager {

    void clearJobOfferCache(JobOffer jobOffer);

    void clearApplicationCache(Application application);
}
