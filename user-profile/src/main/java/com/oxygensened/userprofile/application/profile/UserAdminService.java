package com.oxygensened.userprofile.application.profile;

import com.oxygensened.userprofile.application.cache.event.ClearListCacheEvent;
import com.oxygensened.userprofile.application.technology.TechnologyRepository;
import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import com.oxygensened.userprofile.domain.service.DeveloperOrderService;
import jakarta.persistence.EntityManager;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAdminService.class);
    private final EntityManager entityManager;
    private final TechnologyRepository technologyRepository;
    private final DeveloperOrderService developersOrderService;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public UserAdminService(EntityManager entityManager, TechnologyRepository technologyRepository, DeveloperOrderService developersOrderService,
                            UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.entityManager = entityManager;
        this.technologyRepository = technologyRepository;
        this.developersOrderService = developersOrderService;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Transactional
    @Async
    public void updateDevelopersPopularityRateAsync() {
        updateDevelopersPopularityRate();
    }

    @Transactional
    public void updateDevelopersPopularityRate() {
        LOGGER.info("Started updating developers popularity rate");
        var startTime = Instant.now();

        List<String> technologies = technologyRepository.getFeaturedTechnologies();
        List<User> users = userRepository.findAllDevelopers();
        for (int i = 1; i <= users.size(); i++) {
            developersOrderService.calculateDevelopersPopularityRate(users.get(i - 1), technologies);
            if (i % 200 == 0) {
                entityManager.flush();
            }
        }

        var finishTime = Instant.now();
        LOGGER.info("Finished updating developers popularity rate in %d ms".formatted(Duration.between(startTime, finishTime).toMillis()));
        applicationEventPublisher.publishEvent(ClearListCacheEvent.DEVELOPERS);
    }
}
