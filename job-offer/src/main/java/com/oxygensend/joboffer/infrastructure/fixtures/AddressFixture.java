package com.oxygensend.joboffer.infrastructure.fixtures;

import com.github.javafaker.Faker;
import com.oxygensend.joboffer.domain.entity.Address;
import com.oxygensend.joboffer.domain.repository.AddressRepository;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixtureType;
import com.oxygensend.springfixtures.FixturesFakerProvider;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
class AddressFixture implements Fixture {
    public final static int SIZE = 400;
    private final Faker faker;
    private final AddressRepository addressRepository;

    AddressFixture(FixturesFakerProvider fakerProvider, AddressRepository addressRepository) {
        this.faker = fakerProvider.faker();
        this.addressRepository = addressRepository;
    }

    @Override
    public void load() {
        List<Address> addresses = Stream.generate(() -> new Address(faker.address().cityName(),
                                                                    faker.address().zipCode(),
                                                                    Double.valueOf(faker.address().longitude()),
                                                                    Double.valueOf(faker.address().latitude())))
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
