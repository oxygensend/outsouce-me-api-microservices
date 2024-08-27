package com.oxygensened.userprofile.application.profile;

import com.oxygensened.userprofile.application.auth.AuthRepository;
import com.oxygensened.userprofile.application.auth.dto.CreateUserCommand;
import com.oxygensened.userprofile.application.cache.event.ClearListCacheEvent;
import com.oxygensened.userprofile.application.profile.dto.request.CreateUserRequest;
import com.oxygensened.userprofile.application.technology.TechnologyRepository;
import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import com.oxygensened.userprofile.domain.service.DeveloperOrderService;
import com.oxygensened.userprofile.domain.service.UserIdGenerator;
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
public class UserAdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAdminService.class);
    private final EntityManager entityManager;
    private final TechnologyRepository technologyRepository;
    private final DeveloperOrderService developersOrderService;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AuthRepository authRepository;
    private final UserIdGenerator userIdGenerator;


    public UserAdminService(EntityManager entityManager, TechnologyRepository technologyRepository,
                            DeveloperOrderService developersOrderService,
                            UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher,
                            AuthRepository authRepository, UserIdGenerator userIdGenerator) {
        this.entityManager = entityManager;
        this.technologyRepository = technologyRepository;
        this.developersOrderService = developersOrderService;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.authRepository = authRepository;
        this.userIdGenerator = userIdGenerator;
    }


    @Transactional
    @Async
    public void updateDevelopersPopularityRateAsync() {
        updateDevelopersPopularityRate();
    }

    private void updateDevelopersPopularityRate() {
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
        LOGGER.info("Finished updating developers popularity rate in %d ms".formatted(
            Duration.between(startTime, finishTime).toMillis()));
        applicationEventPublisher.publishEvent(ClearListCacheEvent.DEVELOPERS);
    }

    @Transactional
    public void createUser(CreateUserRequest request) {
        var user = User.builder()
                       .id(userIdGenerator.generate())
                       .accountType(request.accountType())
                       .name(request.name())
                       .surname(request.surname())
                       .email(request.email())
                       .phoneNumber(request.phoneNumber())
                       .externalId(UUID.randomUUID().toString())
                       .build();

        authRepository.createUser(new CreateUserCommand(user, request.password()));
        userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
