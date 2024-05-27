package com.oxygensened.userprofile.infrastructure.fixtures;

import com.github.javafaker.Faker;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixtureType;
import com.oxygensend.springfixtures.FixturesFakerProvider;
import com.oxygensened.userprofile.domain.entity.Address;
import com.oxygensened.userprofile.domain.repository.AddressRepository;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
class AddressFixture implements Fixture {
    public final static int SIZE = 400;
    private final Faker faker;
    private final AddressRepository addressRepository;

    AddressFixture(AddressRepository addressRepository, FixturesFakerProvider fakerProvider) {
        this.addressRepository = addressRepository;
        this.faker = fakerProvider.faker();
    }

    @Override
    public void load() {
        List<Address> addresses = Stream.generate(() -> new Address(faker.address().cityName(), faker.address().zipCode(), faker.address().longitude(), faker.address().latitude()))
                                        .distinct()
                                        .limit(SIZE)
                                        .toList();

        addressRepository.saveAll(addresses);
    }

    @Override
    public boolean isEnabled() {
        return addressRepository.count() == 0;
    }

    @Override
    public String collectionName() {
        return "addresses";
    }

    @Override
    public FixtureType type() {
        return FixtureType.DICTIONARY;
    }
}
