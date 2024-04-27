package com.oxygensend.joboffer.infrastructure.jpa.repository;

import com.oxygensend.joboffer.domain.repository.filter.JobOfferFilter;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
final class JobOfferJpaFacadeRepository implements JobOfferRepository {
    private final JobOfferJpaRepository jobOfferJpaRepository;
    private final EntityManager entityManager;

    JobOfferJpaFacadeRepository(JobOfferJpaRepository jobOfferJpaRepository, EntityManager entityManager) {
        this.jobOfferJpaRepository = jobOfferJpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<JobOffer> findById(Long id) {
        return jobOfferJpaRepository.findById(id);
    }

    @Override
    public JobOffer save(JobOffer jobOffer) {
        return jobOfferJpaRepository.save(jobOffer);
    }

    @Override
    public void delete(JobOffer jobOffer) {
        jobOfferJpaRepository.delete(jobOffer);
    }

    @Override
    public Optional<JobOffer> findBySlug(String slug) {
        return jobOfferJpaRepository.findBySlug(slug);
    }

    @Override
    public long findTheNewestSlugVersion(String slug) {
        return jobOfferJpaRepository.findTheNewestSlugVersion(slug);
    }

    @Override
    public Page<JobOffer> findAll(JobOfferFilter filter, Pageable pageable) {
        var specification = JobOfferSpecifications.getPredicateForJobOfferQuery(filter);
        return JpaUtils.findPageable(entityManager, pageable, JobOffer.class, specification);
    }
}
