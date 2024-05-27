package com.oxygensened.userprofile.infrastructure.fixtures;

import com.github.javafaker.Faker;
import com.oxygensend.springfixtures.Fixture;
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
import java.util.Locale;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
class JobPositionFixture implements Fixture {
    private final static Random RANDOM = new Random(100);
    private final static Faker FAKER = Faker.instance(Locale.UK, RANDOM);

    private final UserRepository userRepository;
    private final JobPositionRepository jobPositionRepository;
    private final CompanyRepository companyRepository;

    JobPositionFixture(UserRepository userRepository, JobPositionRepository jobPositionRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.jobPositionRepository = jobPositionRepository;
        this.companyRepository = companyRepository;
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
            int nbOfPositions = RANDOM.nextInt(3);
            for (int i = 0; i < nbOfPositions; i++) {
                JobPosition jobPosition = JobPosition.builder()
                                                     .name(FAKER.job().position())
                                                     .company(companies.get(RANDOM.nextInt(CompanyFixture.SIZE - 1)))
                                                     .description(FAKER.job().keySkills())
                                                     .individual(user)
                                                     .formOfEmployment(FormOfEmployment.values()[RANDOM.nextInt(FormOfEmployment.values().length - 1)])
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
