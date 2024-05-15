package com.oxygensend.joboffer.domain.repository;

import com.oxygensend.joboffer.domain.JobOfferSearchResult;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.repository.filter.JobOfferFilter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobOfferRepository {
    Optional<JobOffer> findById(Long id);

    JobOffer save(JobOffer jobOffer);

    void delete(JobOffer jobOffer);

    Optional<JobOffer> findBySlug(String slug);

    long findTheNewestSlugVersion(String slug);

    Page<JobOffer> findAll(JobOfferFilter filter, Pageable pageable);

    List<JobOffer> findExpiredJobOffers();

    List<JobOffer> findAll();

    Page<JobOfferSearchResult> search(String query, Pageable pageable);
}
