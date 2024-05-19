package com.oxygensened.userprofile.domain.repository;

import com.oxygensened.userprofile.domain.entity.JobOffer;
import java.util.Optional;

public interface JobOfferRepository {

    Optional<JobOffer> findById(Long id);

    void save(JobOffer jobOffer);

    void deleteById(Long id);
}
