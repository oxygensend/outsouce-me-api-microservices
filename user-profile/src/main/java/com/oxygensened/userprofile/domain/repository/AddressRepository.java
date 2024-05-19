package com.oxygensened.userprofile.domain.repository;

import com.oxygensened.userprofile.domain.entity.Address;
import java.util.Optional;

public interface AddressRepository {

    Optional<Address> findByPostCodeAndCity(String postCode, String city);
}
