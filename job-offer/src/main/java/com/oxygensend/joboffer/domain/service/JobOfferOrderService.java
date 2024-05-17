package com.oxygensend.joboffer.domain.service;

import com.oxygensend.joboffer.domain.entity.JobOffer;
import java.util.List;

public interface JobOfferOrderService {

    void calculateJobOfferPopularityRate(JobOffer jobOffer, List<String> featuredTechnologies);
}
