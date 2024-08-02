package com.oxygensened.userprofile.infrastructure.fixtures;

import com.oxygensend.springfixtures.Fixture;
import com.oxygensend.springfixtures.FixtureType;
import com.oxygensened.userprofile.domain.entity.Address;
import com.oxygensened.userprofile.domain.repository.AddressRepository;
import com.oxygensened.userprofile.infrastructure.services.staticdata.dto.AddressDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AddressFixture implements Fixture {
    public final static int SIZE = 500;
    private final AddressRepository addressRepository;
    private final RestClient restClient;
    private final String staticDataUrl;

    AddressFixture(AddressRepository addressRepository, RestClient restClient, String staticDataUrl) {
        this.addressRepository = addressRepository;
        this.restClient = restClient;
        this.staticDataUrl = staticDataUrl;
    }

    @Override
    public void load() {
        List<Address> addresses = new ArrayList<>();
        List<AddressDto> importedAddresses = restClient.get()
                                                       .uri(staticDataUrl + "/api/v1/static-data/addresses/with-postal-codes")
                                                       .retrieve()
                                                       .body(new ParameterizedTypeReference<>() {
                                                       });

        assert importedAddresses != null;
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
