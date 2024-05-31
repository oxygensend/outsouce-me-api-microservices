package com.oxygensend.joboffer.infrastructure.domain;

import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.entity.User;
import com.oxygensend.joboffer.domain.entity.part.Experience;
import com.oxygensend.joboffer.domain.service.JobOfferOrderService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
class JobOfferOrderServiceImpl implements JobOfferOrderService {


    private static final int LOCALISATION_WAGE = 2;
    private static final int TECHNOLOGY_WAGE = 2;
    private static final Map<Experience, Map<Experience, Double>> EXPERIENCE_WAGES = Map.of(
            Experience.TRAINEE, Map.of(
                    Experience.TRAINEE, 2.0,
                    Experience.JUNIOR, 1.5
            ),
            Experience.JUNIOR, Map.of(
                    Experience.TRAINEE, 1.4,
                    Experience.JUNIOR, 2.0,
                    Experience.MID, 1.5
            ),
            Experience.MID, Map.of(
                    Experience.JUNIOR, 1.2,
                    Experience.MID, 2.0,
                    Experience.SENIOR, 1.2
            ),
            Experience.SENIOR, Map.of(
                    Experience.MID, 1.2,
                    Experience.SENIOR, 2.0,
                    Experience.EXPERT, 1.2
            ),
            Experience.EXPERT, Map.of(
                    Experience.SENIOR, 1.2,
                    Experience.EXPERT, 2.0
            )
    );

    public void calculateJobOfferPopularityRate(JobOffer jobOffer, List<String> featuredTechnologies) {
        double randomRate = new Random().nextInt(100, 10000);

        List<String> jobOfferTechnologies = new ArrayList<>();
        List<String> jobOfferFeaturedTechnologies = new ArrayList<>();

        categorizeTechnologies(jobOffer, featuredTechnologies, jobOfferTechnologies, jobOfferFeaturedTechnologies);

        randomRate = applyTechnologiesWeight(randomRate, jobOfferTechnologies, jobOfferFeaturedTechnologies);
        randomRate = applyRedirectsWeight(randomRate, jobOffer);
        randomRate = applyOpinionsWeight(randomRate, jobOffer);
        randomRate = applyApplicationsWeight(randomRate, jobOffer);

        jobOffer.setPopularityOrder((int) randomRate);
    }

    @Override
    public Stream<JobOffer> sortForYouJobOffers(List<JobOffer> jobOffers, User user) {
        jobOffers.forEach(jobOffer -> calculateJobOfferForYouDisplayOrder(jobOffer, user));

        return jobOffers.parallelStream()
                        .sorted(Comparator.comparingInt(JobOffer::displayOrder));
    }

    private void calculateJobOfferForYouDisplayOrder(JobOffer jobOffer, User user) {
        double randomRate = calculateBaseRate(jobOffer, user);
        randomRate = applyTechnologiesWeight(randomRate, jobOffer, user);
        randomRate = applyExperienceWeight(randomRate, jobOffer, user);
        randomRate = applyOpinionsWeight(randomRate, jobOffer);

        jobOffer.setDisplayOrder((int) randomRate);
    }

    private void categorizeTechnologies(JobOffer jobOffer, List<String> featuredTechnologies,
                                        List<String> jobOfferTechnologies, List<String> jobOfferFeaturedTechnologies) {
        jobOffer.technologies().forEach(technology -> {
            if (featuredTechnologies.contains(technology)) {
                jobOfferFeaturedTechnologies.add(technology);
            } else {
                jobOfferTechnologies.add(technology);
            }
        });
    }

    private double applyTechnologiesWeight(double rate, List<String> jobOfferTechnologies, List<String> jobOfferFeaturedTechnologies) {
        if (!jobOfferTechnologies.isEmpty()) {
            double proportionOfFeaturedTechnologies = (double) jobOfferFeaturedTechnologies.size() / jobOfferTechnologies.size();
            return rate * (1 + proportionOfFeaturedTechnologies);
        }
        return rate;
    }

    private double applyRedirectsWeight(double rate, JobOffer jobOffer) {
        int redirects = jobOffer.redirectCount();
        return rate * (1 + (redirects > 1000 ? (double) redirects / 10000 : (double) redirects / 1000));
    }

    private double applyApplicationsWeight(double rate, JobOffer jobOffer) {
        return rate * (1.2 + (double) jobOffer.numberOfApplications() / 100);
    }

    private double calculateBaseRate(JobOffer jobOffer, User user) {
        var jobOfferAddress = jobOffer.address();
        if (jobOfferAddress == null || (Objects.equals(jobOfferAddress.lat(), user.latitude()) && Objects.equals(jobOfferAddress.lon(), user.longitude()))) {
            return new Random().nextInt(1000, 10000);
        } else {
            double baseRate = new Random().nextInt(100, 1000);
            if (user.latitude() != null && user.longitude() != null && jobOfferAddress.lat() != null && jobOfferAddress.lon() != null) {
                double distance = DistanceCalculator.calculateDistanceBasedOnVincentyFormula(
                        user.latitude(), user.longitude(),
                        jobOfferAddress.lat(), jobOfferAddress.lon());
                baseRate *= (LOCALISATION_WAGE - distance / DistanceCalculator.THE_LONGEST_DISTANCE_BETWEEN_CITIES_IN_POLAND);
            }
            return baseRate;
        }
    }

    private double applyTechnologiesWeight(double rate, JobOffer jobOffer, User user) {
        long technologiesIntersectionCount = user.technologies().stream()
                                                 .filter(jobOffer.technologies()::contains)
                                                 .count();
        if (technologiesIntersectionCount > 0) {
            return rate * (double) (TECHNOLOGY_WAGE * technologiesIntersectionCount) / user.technologies().size();
        }
        return rate;
    }

    private double applyExperienceWeight(double rate, JobOffer jobOffer, User user) {
        if (user.experience() != null && jobOffer.experience() != null) {
            return rate * EXPERIENCE_WAGES.getOrDefault(user.experience(), Map.of()).getOrDefault(jobOffer.experience(), 1.0);
        }

        return rate;
    }

    private double applyOpinionsWeight(double rate, JobOffer jobOffer) {
        int opinionsCount = jobOffer.user().opinionsCount();
        if (opinionsCount > 0) {
            return rate * (1 + jobOffer.user().opinionsRate() * opinionsCount / 100);
        }
        return rate;
    }
}
