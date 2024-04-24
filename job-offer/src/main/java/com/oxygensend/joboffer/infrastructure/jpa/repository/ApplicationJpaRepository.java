package com.oxygensend.joboffer.infrastructure.jpa.repository;

import com.oxygensend.joboffer.domain.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

interface ApplicationJpaRepository extends JpaRepository<Application, Long> {
}
