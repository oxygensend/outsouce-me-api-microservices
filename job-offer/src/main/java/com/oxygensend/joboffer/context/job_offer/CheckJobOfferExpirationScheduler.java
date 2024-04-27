package com.oxygensend.joboffer.context.job_offer;

import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import jakarta.persistence.EntityManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
final class CheckJobOfferExpirationScheduler {

    private final JobOfferRepository jobOfferRepository;
    private final EntityManager entityManager;

    CheckJobOfferExpirationScheduler(JobOfferRepository jobOfferRepository, EntityManager entityManager) {
        this.jobOfferRepository = jobOfferRepository;
        this.entityManager = entityManager;
    }

    @Scheduled(cron = "${job-offers.check-job-offer-expiration-cron}")
    void schedule() {
        jobOfferRepository.findExpiredJobOffers().forEach(jobOffer -> {
            jobOffer.setArchived(true);
        });

        entityManager.flush();
    }
}
