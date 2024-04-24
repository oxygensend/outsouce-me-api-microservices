package com.oxygensend.joboffer.infrastructure.jpa.repository;

import com.oxygensend.joboffer.domain.entity.JobOffer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface JobOfferJpaRepository extends JpaRepository<JobOffer, Long> {

    Optional<JobOffer> findBySlug(String slug);

    @Query("SELECT coalesce( max(cast( substring_index(j.slug, '-', -1) as long)),-1) FROM JobOffer j WHERE j.slug like :slug%")
    long findTheNewestSlugVersion(@Param("slug") String slug);
}
