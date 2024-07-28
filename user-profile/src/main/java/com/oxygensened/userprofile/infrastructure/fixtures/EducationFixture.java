package com.oxygensened.userprofile.infrastructure.fixtures;

import com.github.javafaker.Faker;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixturesFakerProvider;
import com.oxygensened.userprofile.domain.entity.Education;
import com.oxygensened.userprofile.domain.entity.University;
import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.repository.EducationRepository;
import com.oxygensened.userprofile.domain.repository.UniversityRepository;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

class EducationFixture implements Fixture {

    private final static List<String> TITLES = List.of("Magister", "Magister inż.", "Inżynier", "Professor", "Dr inż", "Dr", "Doktor hab", "Licencjat");
    private final Random random;
    private final Faker faker;
    private final UserRepository userRepository;
    private final EducationRepository educationRepository;
    private final UniversityRepository universityRepository;

    EducationFixture(UserRepository userRepository, EducationRepository educationRepository, UniversityRepository universityRepository,
                     FixturesFakerProvider fakerProvider) {
        this.userRepository = userRepository;
        this.educationRepository = educationRepository;
        this.universityRepository = universityRepository;
        this.faker = fakerProvider.faker();
        this.random = fakerProvider.random();
    }

    @Override
    public int order() {
        return 1;
    }

    @Override
    public void load() {
        List<User> users = userRepository.findAll();
        List<University> universities = universityRepository.findAll();
        List<Education> educations = new ArrayList<>();
        for (var user : users) {
            for (int i = 0; i < random.nextInt(0, 3); i++) {
                var education = Education.builder()
                                         .title(TITLES.get(random.nextInt(TITLES.size() - 1)))
                                         .university(universities.get(random.nextInt(UniversityFixture.SIZE - 1)))
                                         .grade((double) random.nextInt(3, 5))
                                         .fieldOfStudy(faker.pokemon().name())
                                         .description(faker.lorem().paragraph(random.nextInt(0, 5)))
                                         .individual(user)
                                         .startDate(LocalDate.of(2020, 8, 11))
                                         .endDate(LocalDate.of(2023, 9, 1))
                                         .build();

                educations.add(education);
            }
        }

        educationRepository.saveAll(educations);
    }

    @Override
    public boolean isEnabled() {
        return educationRepository.count() == 0;
    }

    @Override
    public String collectionName() {
        return "education";
    }
}
