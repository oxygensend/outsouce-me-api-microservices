package com.oxygensened.userprofile.domain.repository;

import com.oxygensened.userprofile.domain.entity.JobOffer;
import com.oxygensened.userprofile.domain.entity.User;
import java.util.List;

public interface JobOfferRepository {
    List<JobOffer> getUserJobOffers(User user);

}
