package com.oxygensened.userprofile.infrastructure.jpa;

import com.oxygensened.userprofile.domain.Address;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface AddressJpaRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByPostCode(String postCode);
}
