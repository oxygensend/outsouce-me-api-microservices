package com.oxygensened.userprofile.domain.repository;

import com.oxygensened.userprofile.domain.entity.Address;
import java.util.List;
import java.util.Optional;

public interface AddressRepository {

    Optional<Address> findByPostCodeAndCity(String postCode, String city);
    long count();

    void saveAll(List<Address> addresses);

    List<Address> findAll();
}
