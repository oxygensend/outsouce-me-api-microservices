package com.oxygensened.userprofile.context.technology;

import com.oxygensened.userprofile.context.technology.dto.AddTechnologiesRequest;
import com.oxygensened.userprofile.context.technology.dto.TechnologyRequest;
import com.oxygensened.userprofile.context.technology.dto.TechnologyView;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import com.oxygensened.userprofile.domain.service.DomainUserService;
import org.springframework.stereotype.Service;

@Service
public class TechnologyService {
    private final UserRepository userRepository;
    private final DomainUserService domainUserService;

    public TechnologyService(UserRepository userRepository, DomainUserService domainUserService) {
        this.userRepository = userRepository;
        this.domainUserService = domainUserService;
    }

    public TechnologyView addTechnology(Long userId, TechnologyRequest request) {
        var user = userRepository.findById(userId)
                                 .orElseThrow(() -> UserNotFoundException.withId(userId));

        domainUserService.addTechnology(user, request.name());
        userRepository.save(user);

        return new TechnologyView(request.name());
    }

    public void deleteTechnology(Long userId, String name) {
        var user = userRepository.findById(userId)
                                 .orElseThrow(() -> UserNotFoundException.withId(userId));

        domainUserService.deleteTechnology(user, name);
        userRepository.save(user);
    }

    public void addTechnologies(Long userId, AddTechnologiesRequest request) {
        var user = userRepository.findById(userId)
                                 .orElseThrow(() -> UserNotFoundException.withId(userId));

        request.technologies().forEach(technology -> domainUserService.addTechnology(user, technology));
        userRepository.save(user);
    }
}
