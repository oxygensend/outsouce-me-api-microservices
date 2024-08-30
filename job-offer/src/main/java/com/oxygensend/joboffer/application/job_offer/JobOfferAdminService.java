package com.oxygensend.joboffer.application.job_offer;

import com.oxygensend.joboffer.application.cache.event.ClearDetailsCacheEvent;
import com.oxygensend.joboffer.application.cache.event.ClearListCacheEvent;
import com.oxygensend.joboffer.application.notifications.NotificationsService;
import com.oxygensend.joboffer.application.technology.TechnologyRepository;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.repository.ApplicationRepository;
import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import com.oxygensend.joboffer.domain.service.JobOfferOrderService;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Service
public class JobOfferAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobOfferAdminService.class);
    private final JobOfferRepository jobOfferRepository;
    private final EntityManager entityManager;
    private final TechnologyRepository technologyRepository;
    private final JobOfferOrderService jobOfferOrderService;
    private final NotificationsService notificationsService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ApplicationRepository applicationRepository;

    JobOfferAdminService(JobOfferRepository jobOfferRepository, EntityManager entityManager,
                         TechnologyRepository technologyRepository, JobOfferOrderService jobOfferOrderService,
                         NotificationsService notificationsService, ApplicationEventPublisher applicationEventPublisher,
                         ApplicationRepository applicationRepository) {
        this.jobOfferRepository = jobOfferRepository;
        this.entityManager = entityManager;
        this.technologyRepository = technologyRepository;
        this.jobOfferOrderService = jobOfferOrderService;
        this.notificationsService = notificationsService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.applicationRepository = applicationRepository;
    }

    public void archiveExpiredJobOffers() {
        jobOfferRepository.findExpiredJobOffers().forEach(jobOffer -> {
            jobOffer.setArchived(true);
            notificationsService.sendJobOfferExpiredNotifications(jobOffer);
            applicationEventPublisher.publishEvent(ClearDetailsCacheEvent.jobOfferCache(jobOffer.slug()));
        });

        applicationEventPublisher.publishEvent(ClearListCacheEvent.JOB_OFFER);
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
        applicationEventPublisher.publishEvent(ClearListCacheEvent.JOB_OFFER);
        LOGGER.info("Finished updating job offer popularity rate in %d ms".formatted(
            Duration.between(startTime, finishTime).toMillis()));
    }

    @Transactional
    public void deleteJobOffer(Long id) {
        jobOfferRepository.deleteById(id);
    }

    @Transactional
    public void archiveJobOffer(Long id) {
        jobOfferRepository.archiveById(id);
    }
}
