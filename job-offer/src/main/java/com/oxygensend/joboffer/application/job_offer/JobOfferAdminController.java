package com.oxygensend.joboffer.application.job_offer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/admin/job-offers")
class JobOfferAdminController {

    private final JobOfferAdminService jobOfferAdminService;

    JobOfferAdminController(JobOfferAdminService jobOfferAdminService) {
        this.jobOfferAdminService = jobOfferAdminService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/recalculate-popularity-rate")
    void recalculatePopularityRate() {
        jobOfferAdminService.updateJobOffersPopularityAsync();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Long id) {
        jobOfferAdminService.deleteJobOffer(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/archive/{id}")
    void archive(@PathVariable Long id) {
        jobOfferAdminService.archiveJobOffer(id);
    }
}
