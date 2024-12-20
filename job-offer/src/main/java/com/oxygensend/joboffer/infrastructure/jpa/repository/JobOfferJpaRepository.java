package com.oxygensend.joboffer.infrastructure.jpa.repository;

import com.oxygensend.joboffer.domain.entity.JobOffer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface JobOfferJpaRepository extends JpaRepository<JobOffer, Long> {

    Optional<JobOffer> findBySlug(String slug);

    @Query("SELECT coalesce( max(cast( substring_index(j.slug, '-', -1) as long)),-1) FROM JobOffer j WHERE j.slug like :slug%")
    long findTheNewestSlugVersion(@Param("slug") String slug);

    List<JobOffer> findAll(Specification<JobOffer> specification);

    @Query("SELECT j FROM JobOffer j JOIN FETCH j.address JOIN FETCH j.user WHERE j.archived = false ")
    List<JobOffer> findAll();

    @Modifying
    @Query("UPDATE JobOffer j SET j.redirectCount = j.redirectCount + 1 WHERE j.slug = :slug")
    void addRedirect(String slug);

    @Modifying
    @Query("UPDATE JobOffer j SET j.archived = true where j.id = :id")
    void archiveById(Long id);

}
