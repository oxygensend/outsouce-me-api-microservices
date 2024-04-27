package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

interface UniversityJpaRepository extends JpaRepository<University, Long> {
}
