package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.entity.Address;
import com.oxygensened.userprofile.domain.repository.AddressRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class AddressFacadeRepository implements AddressRepository {
    private final AddressJpaRepository addressJpaRepository;

    AddressFacadeRepository(AddressJpaRepository addressJpaRepository) {
        this.addressJpaRepository = addressJpaRepository;
    }

    @Override
    public Optional<Address> findByPostCodeAndCity(String postCode, String city) {
        return addressJpaRepository.findByPostCodeAndCity(postCode, city);
    }
}
