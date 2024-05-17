package com.oxygensend.joboffer.context.job_offer;

import com.oxygensend.joboffer.context.technology.TechnologyRepository;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import com.oxygensend.joboffer.domain.service.JobOfferOrderService;
import jakarta.persistence.EntityManager;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class JobOfferAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobOfferAdminService.class);
    private final JobOfferRepository jobOfferRepository;
    private final EntityManager entityManager;
    private final TechnologyRepository technologyRepository;
    private final JobOfferOrderService jobOfferOrderService;

    JobOfferAdminService(JobOfferRepository jobOfferRepository, EntityManager entityManager, TechnologyRepository technologyRepository, JobOfferOrderService jobOfferOrderService) {
        this.jobOfferRepository = jobOfferRepository;
        this.entityManager = entityManager;
        this.technologyRepository = technologyRepository;
        this.jobOfferOrderService = jobOfferOrderService;
    }

    public void archiveExpiredJobOffers() {
        jobOfferRepository.findExpiredJobOffers().forEach(jobOffer -> {
            jobOffer.setArchived(true);
        });

        entityManager.flush();
    }

    @Transactional
    @Async
    public void updateJobOffersPopularityAsync() {
        updateJobOffersPopularity();
    }

    @Transactional
    public void updateJobOffersPopularity() {
        LOGGER.info("Started updating job offer popularity rate");
        var startTime = Instant.now();

        List<String> technologies = technologyRepository.getFeaturedTechnologies();
        List<JobOffer> jobOffers = jobOfferRepository.findAll();
        for (int i = 1; i <= jobOffers.size(); i++) {
            jobOfferOrderService.calculateJobOfferPopularityRate(jobOffers.get(i - 1), technologies);
            if (i % 200 == 0) {
                entityManager.flush();
            }
        }

        var finishTime = Instant.now();
        LOGGER.info("Finished updating job offer popularity rate in %d ms".formatted(Duration.between(startTime, finishTime).toMillis()));
    }
}
