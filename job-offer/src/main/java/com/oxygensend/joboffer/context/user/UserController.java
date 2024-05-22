package com.oxygensend.joboffer.context.user;

import com.oxygensend.joboffer.context.job_offer.JobOfferService;
import com.oxygensend.joboffer.context.job_offer.dto.view.JobOfferInfoView;
import com.oxygensend.joboffer.context.user.dto.CreateUserRequest;
import com.oxygensend.joboffer.context.user.dto.view.UserDetailsView;
import com.oxygensend.joboffer.domain.repository.filter.JobOfferFilter;
import com.oxygensend.joboffer.domain.repository.filter.JobOfferSort;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/job-offers/users")
final class UserController {
    private final UserService userService;
    private final JobOfferService jobOfferService;


    UserController(UserService userService, JobOfferService jobOfferService) {
        this.userService = userService;
        this.jobOfferService = jobOfferService;
    }

    @PostMapping
    UserDetailsView createUser(CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }


    @GetMapping("/{id}/job-offers")
    List<JobOfferInfoView> getUsersJobOffers(@PathVariable String id,
                                             @RequestParam(name = "archived", required = false, defaultValue = "false")
                                             boolean archived) {
        var filter = JobOfferFilter.builder()
                                   .archived(archived)
                                   .userId(id)
                                   .sort(JobOfferSort.POPULAR)
                                   .build();
        return jobOfferService.getJobOfferInfoList(filter);
    }

}