package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.entity.Address;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface AddressJpaRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByPostCode(String postCode);

    Optional<Address> findByPostCodeAndCity(String postCode, String city);

}
