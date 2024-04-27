package com.oxygensend.joboffer.infrastructure.jpa.repository;

import com.oxygensend.joboffer.domain.entity.Address;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface AddressJpaRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByPostCodeAndCity(String postCode, String city);
}
