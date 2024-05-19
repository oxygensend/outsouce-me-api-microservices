package com.oxygensend.joboffer.domain.service;

import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.repository.filter.JobOfferFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobOffersForYou {

    Page<JobOffer> getForUser(String userId, JobOfferFilter filter, Pageable pageable);
}
