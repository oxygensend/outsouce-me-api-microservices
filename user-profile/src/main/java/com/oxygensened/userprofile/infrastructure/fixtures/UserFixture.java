package com.oxygensened.userprofile.infrastructure.fixtures;

import com.github.javafaker.Faker;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensened.userprofile.context.technology.TechnologyRepository;
import com.oxygensened.userprofile.domain.entity.Address;
import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.entity.part.AccountType;
import com.oxygensened.userprofile.domain.entity.part.Experience;
import com.oxygensened.userprofile.domain.repository.AddressRepository;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import com.oxygensened.userprofile.domain.service.UserIdGenerator;
import com.oxygensened.userprofile.infrastructure.services.auth.AuthClient;
import com.oxygensened.userprofile.infrastructure.services.auth.dto.CreateUserRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
class UserFixture implements Fixture {
    public final static int SIZE = 3000;
    private final static Random RANDOM = new Random(100);
    private final static Faker FAKER = Faker.instance(Locale.UK, RANDOM);
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final TechnologyRepository technologyRepository;
    private final UserIdGenerator userIdGenerator;
    private final AuthClient authClient;

    UserFixture(UserRepository userRepository, AddressRepository addressRepository, TechnologyRepository technologyRepository, UserIdGenerator userIdGenerator, AuthClient authClient) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.technologyRepository = technologyRepository;
        this.userIdGenerator = userIdGenerator;
        this.authClient = authClient;
    }

    @Override
    public int order() {
        return Integer.MIN_VALUE;
    }

    @Override
    public void load() {
        List<Address> addresses = addressRepository.findAll();
        List<String> technologies = technologyRepository.getTechnologies();
        List<String> emails = Stream.generate(() -> FAKER.internet().safeEmailAddress())
                                    .distinct()
                                    .limit(SIZE)
                                    .toList();

        for (int i = 0; i < SIZE; i++) {
            var userBuilder = User.builder()
                                  .id(userIdGenerator.generate())
                                  .name(FAKER.name().firstName())
                                  .surname(FAKER.name().lastName())
                                  .slug(FAKER.name().firstName().toLowerCase() + "-" + FAKER.name().lastName().toLowerCase())
                                  .address(addresses.get(RANDOM.nextInt(0, AddressFixture.SIZE - 1)))
                                  .email(emails.get(i))
                                  .dateOfBirth(LocalDate.ofInstant(FAKER.date().birthday().toInstant(), ZoneId.systemDefault()))
                                  .description(FAKER.weather().description())
                                  .externalId(FAKER.internet().uuid())
                                  .linkedinUrl(FAKER.internet().url())
                                  .activeJobPosition(FAKER.job().position())
                                  .phoneNumber(FAKER.phoneNumber().phoneNumber());

            if (i % 3 == 0) {
                userBuilder.accountType(AccountType.PRINCIPLE);
            } else {
                userBuilder.accountType(AccountType.DEVELOPER)
                           .githubUrl(FAKER.company().url())
                           .lookingForJob(RANDOM.nextDouble() < 0.65)
                           .experience(Experience.values()[RANDOM.nextInt(Experience.values().length - 1)])
                           .technologies(getTechnologies(technologies));
            }

            var user = userBuilder.build();

            userRepository.save(userBuilder.build());
            authClient.createUser(CreateUserRequest.fromUser(user, "test123"));
        }


    }

    @Override
    public boolean isEnabled() {
        return userRepository.count() == 0;
    }

    @Override
    public String collectionName() {
        return "users";
    }

    private Set<String> getTechnologies(List<String> list) {
        List<String> shuffledList = new ArrayList<>(list);
        Collections.shuffle(shuffledList, RANDOM);

        int randomSize = RANDOM.nextInt(list.size() + 1);
        return new HashSet<>(shuffledList.subList(0, randomSize));
    }
}
