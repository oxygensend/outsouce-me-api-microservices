package com.oxygensend.joboffer.infrastructure.fixtures;

import com.github.javafaker.Faker;
import com.oxygensend.joboffer.application.technology.TechnologyRepository;
import com.oxygensend.joboffer.domain.entity.Address;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.entity.SalaryRange;
import com.oxygensend.joboffer.domain.entity.User;
import com.oxygensend.joboffer.domain.entity.part.Experience;
import com.oxygensend.joboffer.domain.entity.part.FormOfEmployment;
import com.oxygensend.joboffer.domain.entity.part.SalaryType;
import com.oxygensend.joboffer.domain.entity.part.SupportedCurrency;
import com.oxygensend.joboffer.domain.entity.part.WorkType;
import com.oxygensend.joboffer.domain.repository.AddressRepository;
import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import com.oxygensend.springfixtures.Fixture;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
class JobOfferFixture implements Fixture {

    public final static int SIZE = 10000;

    private final static Random RANDOM = new Random(100);
    private final static Faker FAKER = Faker.instance(Locale.UK, RANDOM);
    private final JobOfferRepository jobOfferRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final TechnologyRepository technologyRepository;


    JobOfferFixture(JobOfferRepository jobOfferRepository, UserRepository userRepository, AddressRepository addressRepository, TechnologyRepository technologyRepository) {
        this.jobOfferRepository = jobOfferRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.technologyRepository = technologyRepository;
    }


    @Override
    public int order() {
        return Integer.MIN_VALUE;
    }

    @Override
    public void load() {
        List<User> principals = userRepository.findAllPrincipals();
        List<Address> addresses = addressRepository.findAll();
        List<String> technologies = technologyRepository.getTechnologies();

        for (int i = 0; i < SIZE; i++) {
            var jobOffer = JobOffer.builder()
                                   .name(FAKER.job().title())
                                   .description(FAKER.lorem().characters(0, 1000))
                                   .experience(Experience.values()[RANDOM.nextInt(Experience.values().length - 1)])
                                   .formOfEmployment(FormOfEmployment.values()[RANDOM.nextInt(FormOfEmployment.values().length - 1)])
                                   .user(principals.get(RANDOM.nextInt(principals.size() - 1)))
                                   .address(RANDOM.nextDouble() < 0.8 ? addresses.get(RANDOM.nextInt(addresses.size() - 1)) : null)
                                   .redirectCount(RANDOM.nextInt(400))
                                   .technologies(getTechnologies(technologies))
                                   .workTypes(Set.of(WorkType.values()[RANDOM.nextInt(WorkType.values().length - 1)]))
                                   .archived(RANDOM.nextDouble() < 0.7)
                                   .validTo(RANDOM.nextDouble() < 0.75 ? FAKER.date().future(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay() : null)
                                   .salaryRange(getSalaryRange())
                                   .build();

            jobOfferRepository.save(jobOffer);
        }

    }

    @Override
    public boolean isEnabled() {
        return jobOfferRepository.count() == 0;
    }

    @Override
    public String collectionName() {
        return "job-offers";
    }

    private SalaryRange getSalaryRange() {
        var downRange = RANDOM.nextDouble(10000);
        var upRange = downRange + RANDOM.nextDouble(10000);
        var currency = SupportedCurrency.values()[RANDOM.nextInt(SupportedCurrency.values().length - 1)];
        var salaryType = SalaryType.values()[RANDOM.nextInt(SalaryType.values().length - 1)];
        return RANDOM.nextDouble() < 0.7 ? new SalaryRange(downRange, upRange, currency, salaryType) : null;
    }

    private Set<String> getTechnologies(List<String> list) {
        List<String> shuffledList = new ArrayList<>(list);
        Collections.shuffle(shuffledList, RANDOM);

        int randomSize = RANDOM.nextInt(list.size() + 1);
        return new HashSet<>(shuffledList.subList(0, randomSize));
    }
}
