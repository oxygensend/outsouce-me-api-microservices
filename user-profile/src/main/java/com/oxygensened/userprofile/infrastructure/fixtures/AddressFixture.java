package com.oxygensened.userprofile.infrastructure.fixtures;

import com.github.javafaker.Faker;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixtureType;
import com.oxygensened.userprofile.domain.entity.Address;
import com.oxygensened.userprofile.domain.repository.AddressRepository;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
class AddressFixture implements Fixture {
    public final static int SIZE = 400;
    private final static Faker FAKER = Faker.instance(Locale.UK, new Random(100));
    private final AddressRepository addressRepository;

    AddressFixture(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void load() {
        List<Address> addresses = Stream.generate(() -> new Address(FAKER.address().cityName(), FAKER.address().zipCode(), FAKER.address().longitude(), FAKER.address().latitude()))
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
