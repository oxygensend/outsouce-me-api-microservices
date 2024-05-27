package com.oxygensened.userprofile.infrastructure.fixtures;

import com.github.javafaker.Faker;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixtureType;
import com.oxygensened.userprofile.domain.entity.Company;
import com.oxygensened.userprofile.domain.repository.CompanyRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
class CompanyFixture implements Fixture {
    public final static int SIZE = 500;
    private final static Faker FAKER = Faker.instance(Locale.UK, new Random(100));
    private final CompanyRepository companyRepository;

    CompanyFixture(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void load() {
        List<Company> companies = new ArrayList<>(SIZE);
        Stream.generate(() -> FAKER.company().name())
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
