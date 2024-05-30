package com.oxygensened.userprofile.context.technology;

import com.oxygensend.commonspring.exception.AccessDeniedException;
import com.oxygensend.commonspring.request_context.RequestContext;
import com.oxygensened.userprofile.context.technology.dto.AddTechnologiesRequest;
import com.oxygensened.userprofile.context.technology.dto.TechnologyRequest;
import com.oxygensened.userprofile.context.technology.dto.TechnologyView;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import com.oxygensened.userprofile.domain.service.DomainUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TechnologyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TechnologyService.class);
    private final UserRepository userRepository;
    private final DomainUserService domainUserService;
    private final RequestContext requestContext;

    public TechnologyService(UserRepository userRepository, DomainUserService domainUserService, RequestContext requestContext) {
        this.userRepository = userRepository;
        this.domainUserService = domainUserService;
        this.requestContext = requestContext;
    }

    public TechnologyView addTechnology(Long userId, TechnologyRequest request) {
        var user = userRepository.findById(userId)
                                 .orElseThrow(() -> UserNotFoundException.withId(userId));

        if (!requestContext.isUserAuthenticated(userId)) {
            LOGGER.info("User {} is not allow to create technology for different entities", requestContext.userIdAsString());
            throw new AccessDeniedException();
        }

        domainUserService.addTechnology(user, request.name());
        userRepository.save(user);

        return new TechnologyView(request.name());
    }

    public void deleteTechnology(Long userId, String name) {
        var user = userRepository.findById(userId)
                                 .orElseThrow(() -> UserNotFoundException.withId(userId));

        if (!requestContext.isUserAuthenticated(userId)) {
            LOGGER.info("User {} is not allow to delete technology for different entities", requestContext.userIdAsString());
            throw new AccessDeniedException();
        }

        domainUserService.deleteTechnology(user, name);
        userRepository.save(user);
    }

    public void addTechnologies(Long userId, AddTechnologiesRequest request) {
        var user = userRepository.findById(userId)
                                 .orElseThrow(() -> UserNotFoundException.withId(userId));

        if (!requestContext.isUserAuthenticated(userId)) {
            LOGGER.info("User {} is not allow to add technologies for different entities", requestContext.userIdAsString());
            throw new AccessDeniedException();
        }

        request.technologies().forEach(technology -> domainUserService.addTechnology(user, technology));
        userRepository.save(user);
    }
}
