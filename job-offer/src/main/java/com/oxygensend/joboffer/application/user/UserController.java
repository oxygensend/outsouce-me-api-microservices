package com.oxygensend.joboffer.application.user;

import com.oxygensend.joboffer.application.cache.CacheData;
import com.oxygensend.joboffer.application.job_offer.JobOfferService;
import com.oxygensend.joboffer.application.job_offer.dto.view.JobOfferInfoView;
import com.oxygensend.joboffer.application.user.dto.CreateUserRequest;
import com.oxygensend.joboffer.application.user.dto.view.UserDetailsView;
import com.oxygensend.joboffer.domain.repository.filter.JobOfferFilter;
import com.oxygensend.joboffer.domain.repository.filter.JobOfferSort;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/job-offers/users")
class UserController {
    private final UserService userService;
    private final JobOfferService jobOfferService;


    UserController(UserService userService, JobOfferService jobOfferService) {
        this.userService = userService;
        this.jobOfferService = jobOfferService;
    }

    @Cacheable(value = CacheData.JOB_OFFER_CACHE, key = CacheData.USERS_JOB_OFFERS)
    @GetMapping("/{id}/job-offers")
    public List<JobOfferInfoView> getUsersJobOffers(@PathVariable String id,
                                                    @RequestParam(name = "archived", required = false, defaultValue = "false")
                                                    boolean archived) {
        var filter = JobOfferFilter.builder()
                                   .archived(archived)
                                   .userId(id)
                                   .sort(JobOfferSort.POPULAR)
                                   .build();
        return jobOfferService.getJobOfferInfoList(filter);
    }

    @PostMapping
    UserDetailsView createUser(CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }

}