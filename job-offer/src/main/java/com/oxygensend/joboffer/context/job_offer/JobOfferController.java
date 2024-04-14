package com.oxygensend.joboffer.context.job_offer;

import com.oxygensend.commons_jdk.DefaultView;
import com.oxygensend.joboffer.context.job_offer.dto.CreateJobOfferRequest;
import com.oxygensend.joboffer.context.job_offer.dto.JobOfferView;
import com.oxygensend.joboffer.context.job_offer.dto.UpdateJobOfferRequest;
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
    JobOfferView create(@Validated @RequestBody CreateJobOfferRequest request) {
      return   jobOfferService.createJobOffer(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{slug}")
    JobOfferView show(@PathVariable String slug) {
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
    JobOfferView update(@PathVariable String slug, @Validated @RequestBody UpdateJobOfferRequest request) {
       return jobOfferService.updateJobOffer(slug, request);
    }

}
