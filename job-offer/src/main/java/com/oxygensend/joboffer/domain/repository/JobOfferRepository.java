package com.oxygensend.joboffer.domain.repository;

import com.oxygensend.joboffer.domain.entity.JobOffer;
import java.util.Optional;

public interface JobOfferRepository {
    Optional<JobOffer> findById(Long id);

    JobOffer save(JobOffer jobOffer);

    void delete(JobOffer jobOffer);

    Optional<JobOffer> findBySlug(String slug);

    long findTheNewestSlugVersion(String slug);
}
