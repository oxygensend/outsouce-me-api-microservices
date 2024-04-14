package com.oxygensend.joboffer.infrastructure.jpa;

import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
final class JobOfferJpaFacadeRepository implements JobOfferRepository {
    private final JobOfferJpaRepository jobOfferJpaRepository;

    JobOfferJpaFacadeRepository(JobOfferJpaRepository jobOfferJpaRepository) {
        this.jobOfferJpaRepository = jobOfferJpaRepository;
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
}
