package com.oxygensened.userprofile.infrastructure.jpa;

import com.oxygensened.userprofile.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;

interface UniversityJpaRepository extends JpaRepository<University, Long> {
}
