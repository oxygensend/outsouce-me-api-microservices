package com.oxygensened.userprofile.infrastructure.fixtures;

import com.github.javafaker.Faker;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixtureType;
import com.oxygensend.springfixtures.FixturesFakerProvider;
import com.oxygensened.userprofile.domain.entity.Company;
import com.oxygensened.userprofile.domain.repository.CompanyRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
class CompanyFixture implements Fixture {
    public final static int SIZE = 500;
    private final Faker faker;
    private final CompanyRepository companyRepository;

    CompanyFixture(CompanyRepository companyRepository, FixturesFakerProvider fakerProvider) {
        this.companyRepository = companyRepository;
        this.faker = fakerProvider.faker();
    }

    @Override
    public void load() {
        List<Company> companies = new ArrayList<>(SIZE);
        Stream.generate(() -> faker.company().name())
              .distinct()
              .limit(SIZE)
              .forEach((company) -> {
                  companies.add(new Company(company));
              });
        companyRepository.saveAll(companies);
    }

    @Override
    public boolean isEnabled() {
        return companyRepository.count() == 0;
    }

    @Override
    public String collectionName() {
        return "companies";
    }

    @Override
    public FixtureType type() {
        return FixtureType.DICTIONARY;
    }


}
