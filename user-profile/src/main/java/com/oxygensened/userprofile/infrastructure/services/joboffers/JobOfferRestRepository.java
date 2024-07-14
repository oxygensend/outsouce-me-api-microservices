package com.oxygensened.userprofile.infrastructure.services.joboffers;

import com.oxygensened.userprofile.domain.entity.JobOffer;
import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.repository.JobOfferRepository;
import com.oxygensened.userprofile.infrastructure.services.joboffers.dto.JobOfferResponse;
import java.util.List;

public class JobOfferRestRepository implements JobOfferRepository {
    private final JobOfferClient jobOfferClient;

    JobOfferRestRepository(JobOfferClient jobOfferClient) {
        this.jobOfferClient = jobOfferClient;
    }

    @Override
    public List<JobOffer> getUserJobOffers(User user) {
        return jobOfferClient.getUserJobOffers(user.id()).stream()
                             .map(JobOfferResponse::toEntity)
                             .toList();
    }
}
