package com.oxygensened.userprofile.infrastructure.fixtures;

import com.github.javafaker.Faker;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixtureType;
import com.oxygensened.userprofile.domain.entity.University;
import com.oxygensened.userprofile.domain.repository.UniversityRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
class UniversityFixture implements Fixture {

    public final static int SIZE = 100;
    private final static Faker FAKER = Faker.instance(Locale.UK, new Random(100));

    private final UniversityRepository universityRepository;

    UniversityFixture(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Override
    public FixtureType type() {
        return FixtureType.DICTIONARY;
    }

    @Override
    public void load() {
        List<University> universities = new ArrayList<>(100);
        Stream.generate(() -> FAKER.university().name())
              .distinct()
              .limit(SIZE)
              .forEach((university) -> {
                  universities.add(new University(university));
              });
        universityRepository.saveAll(universities);
    }

    @Override
    public boolean isEnabled() {
        return universityRepository.count() == 0;
    }

    @Override
    public String collectionName() {
        return "university";
    }
}
