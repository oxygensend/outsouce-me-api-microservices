package com.oxygensend.joboffer.infrastructure.jpa.repository;

import com.oxygensend.joboffer.domain.entity.Address;
import com.oxygensend.joboffer.domain.repository.AddressRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
final class AddressJpaFacadeRepository implements AddressRepository {
    private final AddressJpaRepository addressJpaRepository;

    AddressJpaFacadeRepository(AddressJpaRepository addressJpaRepository) {
        this.addressJpaRepository = addressJpaRepository;
    }

    @Override
    public Optional<Address> findByPostCodeAndCity(String postCode, String city) {
        return addressJpaRepository.findByPostCodeAndCity(postCode, city);
    }

    @Override
    public long count() {
        return addressJpaRepository.count();
    }

    @Override
    public void saveAll(Iterable<Address> addresses) {
        addressJpaRepository.saveAll(addresses);
    }

    @Override
    public List<Address> findAll() {
        return addressJpaRepository.findAll();
    }
}
