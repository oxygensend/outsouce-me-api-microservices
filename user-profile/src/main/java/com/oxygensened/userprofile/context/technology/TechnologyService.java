package com.oxygensened.userprofile.context.technology;

import com.oxygensened.userprofile.context.technology.dto.TechnologyRequest;
import com.oxygensened.userprofile.context.technology.dto.TechnologyView;
import com.oxygensened.userprofile.domain.service.DomainUserService;
import com.oxygensened.userprofile.domain.UserRepository;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
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

    public void deleteTechnology(Long userId, TechnologyRequest request) {
        var user = userRepository.findById(userId)
                                 .orElseThrow(() -> UserNotFoundException.withId(userId));

        domainUserService.deleteTechnology(user, request.name());
        userRepository.save(user);
    }
}
