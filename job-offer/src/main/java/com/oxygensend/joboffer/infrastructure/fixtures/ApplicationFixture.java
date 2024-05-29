package com.oxygensend.joboffer.infrastructure.fixtures;

import com.github.javafaker.Faker;
import com.oxygensend.joboffer.domain.entity.Application;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.entity.User;
import com.oxygensend.joboffer.domain.entity.part.ApplicationStatus;
import com.oxygensend.joboffer.domain.repository.ApplicationRepository;
import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixturesFakerProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

class ApplicationFixture implements Fixture {

    private final Faker faker;
    private final Random random;
    private final ApplicationRepository applicationRepository;
    private final JobOfferRepository jobOfferRepository;
    private final UserRepository userRepository;

    ApplicationFixture(FixturesFakerProvider fakerProvider, ApplicationRepository applicationRepository, JobOfferRepository jobOfferRepository, UserRepository userRepository) {
        this.faker = fakerProvider.faker();
        this.random = fakerProvider.random();
        this.applicationRepository = applicationRepository;
        this.jobOfferRepository = jobOfferRepository;
        this.userRepository = userRepository;
    }

    @Override
    public int order() {
        return 1;
    }

    @Override
    public void load() {
        List<JobOffer> jobOffers = jobOfferRepository.findAll();
        List<User> users = userRepository.findAllDevelopers();
        List<Application> applications = new ArrayList<>();
        for (var jobOffer : jobOffers) {
            for (int i = 0; i < random.nextInt(0, 8); i++) {
                var user = users.get(random.nextInt(users.size() - 1));
                applications.add(new Application(user,
                                                 jobOffer,
                                                 ApplicationStatus.values()[random.nextInt(ApplicationStatus.values().length - 1)],
                                                 faker.lorem().characters(0, 255),
                                                 random.nextDouble() < 0.3));
                jobOffer.increaseNumberOfApplications();
            }
        }

        applicationRepository.saveAll(applications);
    }

    @Override
    public boolean isEnabled() {
        return applicationRepository.count() == 0;
    }

    @Override
    public String collectionName() {
        return "applications";
    }
}
