package com.oxygensend.joboffer.domain.repository;

import com.oxygensend.joboffer.domain.entity.Address;
import java.util.Optional;

public interface AddressRepository {

    Optional<Address> findByPostCodeAndCity(String postCode, String city);
}
