package com.oxygensened.userprofile.infrastructure.fixtures;

import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixtureType;
import com.oxygensened.userprofile.domain.entity.Address;
import com.oxygensened.userprofile.domain.repository.AddressRepository;
import com.oxygensened.userprofile.infrastructure.services.staticdata.StaticDataClient;
import com.oxygensened.userprofile.infrastructure.services.staticdata.dto.AddressDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

class AddressFixture implements Fixture {
    public final static int SIZE = 500;
    private final AddressRepository addressRepository;
    private final StaticDataClient staticDataClient;

    AddressFixture(AddressRepository addressRepository, StaticDataClient staticDataClient) {
        this.addressRepository = addressRepository;
        this.staticDataClient = staticDataClient;
    }

    @Override
    public void load() {
        List<Address> addresses = new ArrayList<>();
        List<AddressDto> importedAddresses = staticDataClient.getAddresses();
        Collections.shuffle(importedAddresses);

        for (int i = 0; i < SIZE; i++) {
            var address = importedAddresses.get(i);
            addresses.add(new Address(address.city(), address.postalCodes().getFirst(), address.lat(), address.lon()));
        }

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
