package com.oxygensened.userprofile.infrastructure.fixtures;

import com.github.javafaker.Faker;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixturesFakerProvider;
import com.oxygensened.userprofile.domain.entity.Company;
import com.oxygensened.userprofile.domain.entity.JobPosition;
import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.entity.part.FormOfEmployment;
import com.oxygensened.userprofile.domain.repository.CompanyRepository;
import com.oxygensened.userprofile.domain.repository.JobPositionRepository;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

class JobPositionFixture implements Fixture {
    private final Random random;
    private final Faker faker;
    private final UserRepository userRepository;
    private final JobPositionRepository jobPositionRepository;
    private final CompanyRepository companyRepository;

    JobPositionFixture(UserRepository userRepository, JobPositionRepository jobPositionRepository, CompanyRepository companyRepository,
                       FixturesFakerProvider fakerProvider) {
        this.userRepository = userRepository;
        this.jobPositionRepository = jobPositionRepository;
        this.companyRepository = companyRepository;
        this.random = fakerProvider.random();
        this.faker = fakerProvider.faker();
    }

    @Override
    public int order() {
        return 3;
    }

    @Override
    public void load() {
        List<User> users = userRepository.findAll();
        List<Company> companies = companyRepository.findAll();
        List<JobPosition> jobPositions = new ArrayList<>();
        for (var user : users) {
            int nbOfPositions = random.nextInt(3);
            for (int i = 0; i < nbOfPositions; i++) {
                JobPosition jobPosition = JobPosition.builder()
                                                     .name(faker.job().position())
                                                     .company(companies.get(random.nextInt(CompanyFixture.SIZE - 1)))
                                                     .description(faker.lorem().paragraph(random.nextInt(0, 5)))
                                                     .individual(user)
                                                     .formOfEmployment(FormOfEmployment.values()[random.nextInt(FormOfEmployment.values().length - 1)])
                                                     .startDate(LocalDate.of(2020, 8, 11))
                                                     .endDate(nbOfPositions - 1 == i ? null : LocalDate.of(2023, 9, 1))
                                                     .active(nbOfPositions - 1 == i)
                                                     .build();

                jobPositions.add(jobPosition);
            }
        }

        jobPositionRepository.saveAll(jobPositions);
    }

    @Override
    public boolean isEnabled() {
        return jobPositionRepository.count() == 0;
    }

    @Override
    public String collectionName() {
        return "job positions";
    }
}
