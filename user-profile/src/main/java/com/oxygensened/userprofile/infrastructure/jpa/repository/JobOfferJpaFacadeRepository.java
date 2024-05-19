package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.entity.JobOffer;
import com.oxygensened.userprofile.domain.repository.JobOfferRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
class JobOfferJpaFacadeRepository implements JobOfferRepository {

    private final JobOfferJpaRepository jobOfferJpaRepository;

    JobOfferJpaFacadeRepository(JobOfferJpaRepository jobOfferJpaRepository) {
        this.jobOfferJpaRepository = jobOfferJpaRepository;
    }

    public Optional<JobOffer> findById(Long id) {
        return jobOfferJpaRepository.findById(id);
    }

    @Override
    public void save(JobOffer jobOffer) {
        jobOfferJpaRepository.save(jobOffer);
    }

    @Override
    public void deleteById(Long id) {
        jobOfferJpaRepository.deleteById(id);
    }

}
