package com.oxygensened.userprofile.infrastructure.jpa;

import com.oxygensened.userprofile.domain.Company;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface CompanyJpaRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
}
