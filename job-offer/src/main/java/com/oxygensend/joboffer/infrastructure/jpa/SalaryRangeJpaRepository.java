package com.oxygensend.joboffer.infrastructure.jpa;

import com.oxygensend.joboffer.domain.entity.SalaryRange;
import org.springframework.data.jpa.repository.JpaRepository;

interface SalaryRangeJpaRepository extends JpaRepository<SalaryRange, Long> {
}
