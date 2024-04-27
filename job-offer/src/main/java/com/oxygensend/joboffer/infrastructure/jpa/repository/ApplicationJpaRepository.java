package com.oxygensend.joboffer.infrastructure.jpa.repository;

import com.oxygensend.joboffer.domain.entity.Application;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

interface ApplicationJpaRepository extends JpaRepository<Application, Long> {
    boolean existsByJobOfferAndUser(JobOffer jobOffer, User user);

}
