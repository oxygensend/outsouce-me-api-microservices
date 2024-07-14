package com.oxygensened.userprofile.infrastructure.services.joboffers;

import com.oxygensened.userprofile.infrastructure.services.joboffers.dto.JobOfferResponse;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

interface JobOfferClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/job-offers/users/{id}/job-offers-order")
    List<JobOfferResponse> getUserJobOffers(@PathVariable Long id);
}
