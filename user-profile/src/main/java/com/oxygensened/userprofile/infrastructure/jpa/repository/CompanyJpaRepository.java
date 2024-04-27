package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.entity.Company;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface CompanyJpaRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
}
