package com.oxygensend.joboffer.context.job_offer;

import com.oxygensend.commons_jdk.PagedListView;
import com.oxygensend.joboffer.context.JobOfferSort;
import com.oxygensend.joboffer.context.job_offer.dto.CreateJobOfferRequest;
import com.oxygensend.joboffer.context.job_offer.dto.JobOfferView;
import com.oxygensend.joboffer.context.job_offer.dto.JobOfferDetailsView;
import com.oxygensend.joboffer.context.job_offer.dto.UpdateJobOfferRequest;
import com.oxygensend.joboffer.domain.FormOfEmployment;
import com.oxygensend.joboffer.domain.JobOfferFilter;
import com.oxygensend.joboffer.domain.WorkType;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/job-offers")
class JobOfferController {

    private final JobOfferService jobOfferService;

    JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    JobOfferDetailsView create(@Validated @RequestBody CreateJobOfferRequest request) {
        return jobOfferService.createJobOffer(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{slug}")
    JobOfferDetailsView show(@PathVariable String slug) {
        return jobOfferService.getJobOffer(slug);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{slug}/addRedirect")
    void addRedirect(@PathVariable String slug) {
        jobOfferService.addRedirect(slug);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{slug}")
    void delete(@PathVariable String slug) {
        jobOfferService.deleteJobOffer(slug);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{slug}")
    JobOfferDetailsView update(@PathVariable String slug, @Validated @RequestBody UpdateJobOfferRequest request) {
        return jobOfferService.updateJobOffer(slug, request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    PagedListView<JobOfferView> list(@RequestParam(name = "workTypes", required = false) List<WorkType> workTypes,
                                     @RequestParam(name = "technologies", required = false) List<String> technologies,
                                     @RequestParam(name = "address.postCode", required = false) String postCode,
                                     @RequestParam(name = "address.city", required = false) String city,
                                     @RequestParam(name = "formOfEmployments", required = false) List<FormOfEmployment> formOfEmployments,
                                     @RequestParam(name = "archived", required = false, defaultValue = "true") Boolean archived,
                                     @RequestParam(name = "userId", required = false) String userId,
                                     @RequestParam(name = "sort", required = false, defaultValue = "NEWEST") JobOfferSort sort,
                                     Pageable pageable) {

        var filter = JobOfferFilter.builder()
                                   .workTypes(workTypes)
                                   .technologies(technologies)
                                   .formOfEmployments(formOfEmployments)
                                   .postCode(postCode)
                                   .city(city)
                                   .archived(archived)
                                   .userId(userId)
                                   .sort(sort)
                                   .build();

        return jobOfferService.getPaginatedJobOffers(filter, pageable);
    }
}