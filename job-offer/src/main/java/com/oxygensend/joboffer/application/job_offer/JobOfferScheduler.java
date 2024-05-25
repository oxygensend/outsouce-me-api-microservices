package com.oxygensend.joboffer.application.job_offer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
final class JobOfferScheduler {

    private final JobOfferAdminService jobOfferAdminService;

    JobOfferScheduler(JobOfferAdminService jobOfferAdminService) {
        this.jobOfferAdminService = jobOfferAdminService;
    }

    @Scheduled(cron = "${job-offers.recalculate-job-offers-popularity-rate-cron}")
    void schedulePopularityOrderRecalculation() {
        jobOfferAdminService.updateJobOffersPopularity();
    }

    @Scheduled(cron = "${job-offers.check-job-offer-expiration-cron}")
    void scheduleJobOfferExpirationCheck() {
        jobOfferAdminService.archiveExpiredJobOffers();
    }
}
