package com.oxygensend.joboffer.domain.repository;

import com.oxygensend.joboffer.domain.entity.Address;
import java.util.List;
import java.util.Optional;

public interface AddressRepository {

    Optional<Address> findByPostCodeAndCity(String postCode, String city);
    long count();
    void saveAll(Iterable<Address> addresses);

    List<Address> findAll();
}
