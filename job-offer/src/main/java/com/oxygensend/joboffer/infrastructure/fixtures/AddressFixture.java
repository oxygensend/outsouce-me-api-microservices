package com.oxygensend.joboffer.infrastructure.fixtures;

import com.oxygensend.joboffer.domain.entity.Address;
import com.oxygensend.joboffer.domain.repository.AddressRepository;
import com.oxygensend.joboffer.infrastructure.services.staticdata.StaticDataClient;
import com.oxygensend.joboffer.infrastructure.services.staticdata.dto.AddressDto;
import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixtureType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

class AddressFixture implements Fixture {
    public final static int SIZE = 3000;
    private final StaticDataClient staticDataClient;
    private final AddressRepository addressRepository;

    AddressFixture(StaticDataClient staticDataClient, AddressRepository addressRepository) {
        this.staticDataClient = staticDataClient;
        this.addressRepository = addressRepository;
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
