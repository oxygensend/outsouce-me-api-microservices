package com.oxygensened.userprofile.infrastructure.fixtures;

import com.github.javafaker.Faker;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixturesFakerProvider;
import com.oxygensened.userprofile.domain.entity.Language;
import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.repository.LanguageRepository;
import com.oxygensened.userprofile.domain.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class LanguageFixture implements Fixture {
    private final Random random;
    private final Faker faker;
    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;

    LanguageFixture(UserRepository userRepository, LanguageRepository languageRepository,
                    FixturesFakerProvider fakerProvider) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.random = fakerProvider.random();
        this.faker = fakerProvider.faker();
    }

    @Override
    public void load() {
        List<User> users = userRepository.findAll();
        List<Language> languages = new ArrayList<>();
        for (var user : users) {
            for (int i = 0; i < random.nextInt(0, 3); i++) {
                languages.add(new Language(user,
                                           faker.nation().language(),
                                           faker.job().keySkills()));
            }
        }

        languageRepository.saveAll(languages);
    }

    @Override
    public boolean isEnabled() {
        return languageRepository.count() == 0;
    }

    @Override
    public String collectionName() {
        return "languages";
    }
}
