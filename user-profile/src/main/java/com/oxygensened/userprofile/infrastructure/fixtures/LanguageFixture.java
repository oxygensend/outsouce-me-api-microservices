package com.oxygensened.userprofile.infrastructure.fixtures;

import com.github.javafaker.Faker;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensened.userprofile.domain.entity.Language;
import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.repository.LanguageRepository;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
class LanguageFixture implements Fixture {
    private final static Random RANDOM = new Random(100);
    private final static Faker FAKER = Faker.instance(Locale.UK, RANDOM);
    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;

    LanguageFixture(UserRepository userRepository, LanguageRepository languageRepository) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    public void load() {
        List<User> users = userRepository.findAll();
        List<Language> languages = new ArrayList<>();
        for (var user : users) {
            for (int i = 0; i < RANDOM.nextInt(0, 3); i++) {
                languages.add(new Language(user,
                                           FAKER.nation().language(),
                                           FAKER.lorem().characters(0, 1000)));
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
