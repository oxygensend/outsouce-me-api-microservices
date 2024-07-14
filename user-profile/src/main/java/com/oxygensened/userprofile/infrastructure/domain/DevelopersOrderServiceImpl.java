package com.oxygensened.userprofile.infrastructure.domain;

import com.oxygensened.userprofile.domain.entity.Address;
import com.oxygensened.userprofile.domain.entity.JobOffer;
import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.repository.JobOfferRepository;
import com.oxygensened.userprofile.domain.service.DeveloperOrderService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

@Service
class DevelopersOrderServiceImpl implements DeveloperOrderService {

    private static final int TECHNOLOGY_WAGE = 2;
    private final JobOfferRepository jobOfferRepository;

    DevelopersOrderServiceImpl(JobOfferRepository jobOfferRepository) {
        this.jobOfferRepository = jobOfferRepository;
    }

    public void calculateDevelopersPopularityRate(User user, List<String> featuredTechnologies) {
        double randomRate = new Random().nextInt(100, 10000);

        List<String> userTechnologies = new ArrayList<>();
        List<String> userFeaturedTechnologies = new ArrayList<>();

        categorizeTechnologies(user.technologies(), featuredTechnologies, userTechnologies, userFeaturedTechnologies);

        randomRate = applyTechnologiesWeight(randomRate, userTechnologies, userFeaturedTechnologies);
        randomRate = applyRedirectsWeight(randomRate, user.redirectCount());
        randomRate = applyOpinionsWeight(randomRate, user.opinionsCount(), user.opinionsRate());

        user.setPopularityOrder((int) randomRate);
    }

    @Override
    public Stream<User> sortDeveloperForYou(List<User> developers, User user) {
        Set<ImmutablePair<String, String>> jobOffersLocalizationsSet = new HashSet<>();
        Set<String> jobOffersTechnologiesSet = new HashSet<>();
        List<JobOffer> jobOffers = jobOfferRepository.getUserJobOffers(user);

        for (var jobOffer: jobOffers) {
            jobOffersLocalizationsSet.add(new ImmutablePair<>(jobOffer.lon(), jobOffer.lat()));
            jobOffersTechnologiesSet.addAll(jobOffer.technologies());
        }

        developers.forEach(developer -> calculateDeveloperForYouDisplayOrder(developer, jobOffersLocalizationsSet, jobOffersTechnologiesSet));
        return developers.parallelStream()
                         .sorted(Comparator.comparingInt(User::displayOrder));
    }

    private void calculateDeveloperForYouDisplayOrder(User developer, Set<ImmutablePair<String, String>> jobOffersLocalizationsSet,
                                                      Set<String> jobOffersTechnologiesSet) {
        double randomRate = generateInitialRateBasedOnLocation(developer.address(), jobOffersLocalizationsSet);
        randomRate = applyTechnologiesWeight(randomRate, developer.technologies(), jobOffersTechnologiesSet);
        randomRate = applyOpinionsWeight(randomRate, developer.opinionsCount(), developer.opinionsRate());
        developer.setDisplayOrder((int) randomRate);
    }

    private double applyTechnologiesWeight(double rate, List<String> technologies, List<String> featuredTechnologies) {
        if (!technologies.isEmpty()) {
            double proportionOfFeaturedTechnologies = (double) featuredTechnologies.size() / technologies.size();
            return rate * (1 + proportionOfFeaturedTechnologies);
        }
        return rate;
    }

    private double applyRedirectsWeight(double rate, int redirects) {
        return rate * (1 + (redirects > 1000 ? (double) redirects / 10000 : (double) redirects / 1000));
    }

    private double applyOpinionsWeight(double rate, int opinionsCount, double opinionsRate) {
        if (opinionsCount > 0) {
            return rate * (1 + opinionsRate * opinionsCount / 100);
        }
        return rate;
    }

    private void categorizeTechnologies(Set<String> userTechnologies, List<String> featuredTechnologies,
                                        List<String> categorizedTechnologies, List<String> categorizedFeaturedTechnologies) {
        userTechnologies.forEach(technology -> {
            if (featuredTechnologies.contains(technology)) {
                categorizedFeaturedTechnologies.add(technology);
            } else {
                categorizedTechnologies.add(technology);
            }
        });
    }

    private double generateInitialRateBasedOnLocation(Address developerAddress, Set<ImmutablePair<String, String>> jobOffersLocalizationsSet) {
        if (developerAddress == null || jobOffersLocalizationsSet.contains(new ImmutablePair<>(developerAddress.lon(), developerAddress.lat()))) {
            return new Random().nextInt(1000, 10000);
        } else {
            return new Random().nextInt(100, 1000);
        }
    }

    private double applyTechnologiesWeight(double rate, Set<String> developerTechnologies, Set<String> jobOffersTechnologiesSet) {
        long technologiesIntersectionCount = developerTechnologies.stream()
                                                                  .filter(jobOffersTechnologiesSet::contains)
                                                                  .count();
        if (technologiesIntersectionCount > 0) {
            return rate * (double) (TECHNOLOGY_WAGE * technologiesIntersectionCount) / developerTechnologies.size();
        }
        return rate;
    }
}
