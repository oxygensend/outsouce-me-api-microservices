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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
class ApplicationFixture implements Fixture {

    private final static Random RANDOM = new Random(100);
    private final static Faker FAKER = Faker.instance(Locale.UK, RANDOM);
    private final ApplicationRepository applicationRepository;
    private final JobOfferRepository jobOfferRepository;
    private final UserRepository userRepository;

    ApplicationFixture(ApplicationRepository applicationRepository, JobOfferRepository jobOfferRepository, UserRepository userRepository) {
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
            for (int i = 0; i < RANDOM.nextInt(0, 8); i++) {
                var user = users.get(RANDOM.nextInt(users.size() - 1));
                applications.add(new Application(user,
                                                 jobOffer,
                                                 ApplicationStatus.values()[RANDOM.nextInt(ApplicationStatus.values().length - 1)],
                                                 FAKER.lorem().characters(0, 255),
                                                 RANDOM.nextDouble() < 0.3));
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
