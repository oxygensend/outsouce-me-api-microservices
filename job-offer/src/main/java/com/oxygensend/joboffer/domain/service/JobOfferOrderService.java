package com.oxygensend.joboffer.domain.service;

import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.entity.User;
import java.util.List;
import java.util.stream.Stream;

public interface JobOfferOrderService {


    void calculateJobOfferPopularityRate(JobOffer jobOffer, List<String> featuredTechnologies);


    Stream<JobOffer> sortForYouJobOffers(List<JobOffer> jobOffers, User user);
}
