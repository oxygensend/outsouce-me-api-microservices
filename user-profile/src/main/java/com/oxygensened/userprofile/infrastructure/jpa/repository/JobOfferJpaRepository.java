package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.entity.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

interface JobOfferJpaRepository extends JpaRepository<JobOffer, Long> {

}
