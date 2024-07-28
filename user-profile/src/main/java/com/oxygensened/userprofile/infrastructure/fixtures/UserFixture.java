package com.oxygensened.userprofile.infrastructure.fixtures;

import com.github.javafaker.Faker;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixturesFakerProvider;
import com.oxygensened.userprofile.application.technology.TechnologyRepository;
import com.oxygensened.userprofile.application.technology.dto.TechnologyDto;
import com.oxygensened.userprofile.domain.entity.Address;
import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.entity.part.AccountType;
import com.oxygensened.userprofile.domain.entity.part.Experience;
import com.oxygensened.userprofile.domain.repository.AddressRepository;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import com.oxygensened.userprofile.domain.service.UserIdGenerator;
import com.oxygensened.userprofile.infrastructure.services.auth.dto.CreateUserRequest;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

class UserFixture implements Fixture {
    public final static int SIZE = 3000;
    private final Random random;
    private final Faker faker;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final TechnologyRepository technologyRepository;
    private final UserIdGenerator userIdGenerator;
    private final RestClient restClient;
    private final String authServerUrl;
    private final String staticDataUrl;

    UserFixture(UserRepository userRepository, AddressRepository addressRepository,
                TechnologyRepository technologyRepository,
                UserIdGenerator userIdGenerator, FixturesFakerProvider fakerProvider,
                RestClient restClient, String authServerUrl, String staticDataUrl) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.technologyRepository = technologyRepository;
        this.userIdGenerator = userIdGenerator;
        this.restClient = restClient;
        this.authServerUrl = authServerUrl;
        this.random = fakerProvider.random();
        this.faker = fakerProvider.faker();
        this.staticDataUrl = staticDataUrl;
    }

    @Override
    public int order() {
        return Integer.MIN_VALUE;
    }

    @Override
    public void load() {
        List<Address> addresses = addressRepository.findAll();
        List<String> technologies = Stream.ofNullable(restClient.get()
                                                                .uri(staticDataUrl + "/api/v1/static-data/technologies")
                                                                .retrieve()
                                                                .body(TechnologyDto[].class))
                                          .flatMap(Stream::of)
                                          .map(TechnologyDto::name)
                                          .toList();
        List<String> emails = Stream.generate(() -> faker.internet().safeEmailAddress())
                                    .distinct()
                                    .limit(SIZE)
                                    .toList();

        for (int i = 0; i < SIZE; i++) {
            var userBuilder = User.builder()
                                  .id(userIdGenerator.generate())
                                  .name(faker.name().firstName())
                                  .surname(faker.name().lastName())
                                  .slug(faker.name().firstName().toLowerCase() + "-" + faker.name().lastName()
                                                                                            .toLowerCase())
                                  .address(addresses.get(random.nextInt(0, AddressFixture.SIZE - 1)))
                                  .email(emails.get(i))
                                  .dateOfBirth(
                                      LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()))
                                  .description(faker.lorem().paragraph(random.nextInt(0, 10)))
                                  .externalId(faker.internet().uuid())
                                  .linkedinUrl(faker.internet().url())
                                  .activeJobPosition(faker.job().position())
                                  .phoneNumber(faker.phoneNumber().phoneNumber());

            if (i % 3 == 0) {
                userBuilder.accountType(AccountType.PRINCIPLE);
            } else {
                userBuilder.accountType(AccountType.DEVELOPER)
                           .githubUrl(faker.company().url())
                           .lookingForJob(random.nextDouble() < 0.65)
                           .experience(Experience.values()[random.nextInt(Experience.values().length - 1)])
                           .technologies(getTechnologies(technologies));
            }

            var user = userBuilder.build();

            userRepository.save(userBuilder.build());
            restClient.post()
                      .uri(authServerUrl + "/v1/users/create")
                      .contentType(MediaType.APPLICATION_JSON)
                      .body(CreateUserRequest.fromUser(user, "test123"))
                      .retrieve();

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
        Collections.shuffle(shuffledList, random);

        int randomSize = random.nextInt(list.size() + 1);
        return new HashSet<>(shuffledList.subList(0, randomSize));
    }
}
