package com.oxygensend.joboffer.infrastructure.domain;

import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.service.JobOfferOrderService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
class JobOfferOrderServiceImpl implements JobOfferOrderService {


    public void calculateJobOfferPopularityRate(JobOffer jobOffer, List<String> featuredTechnologies) {
        double randomRate = new Random().nextInt(100, 10000);

        List<String> jobOfferTechnologies = new ArrayList<>();
        List<String> jobOfferFeaturedTechnologies = new ArrayList<>();


        jobOffer.technologies().forEach(technology -> {
            if (featuredTechnologies.contains(technology)) {
                jobOfferFeaturedTechnologies.add(technology);
            } else {
                jobOfferTechnologies.add(technology);
            }
        });

        if (!jobOfferTechnologies.isEmpty()) {
            var proportionOfFeaturedTechnologies = jobOfferFeaturedTechnologies.size() / jobOfferTechnologies.size();
            randomRate *= (1 + proportionOfFeaturedTechnologies);
        }

        // REDIRECTS
        var redirects = jobOffer.redirectCount();
        randomRate *= (1 + (redirects > 1000 ? (double) redirects / 10000 : (double) redirects / 1000));

        // OPINIONS
        int opinionsCount = jobOffer.user().opinionsCount();
        if (opinionsCount > 0) {
            randomRate *= (1 + jobOffer.user().opinionsRate() * opinionsCount / 100);
        }

        randomRate *= (1.2 + (double) jobOffer.numberOfApplications() / 100);

        jobOffer.setPopularityOrder((int) randomRate);
    }
}
