package com.oxygensened.userprofile.domain;

import java.util.Optional;

public interface AddressRepository {

    Optional<Address> findByPostCode(String postCode);
}
