package com.oxygensened.userprofile.context.technology;

import com.oxygensened.userprofile.context.technology.dto.TechnologyRequest;
import com.oxygensened.userprofile.context.technology.dto.TechnologyView;
import com.oxygensened.userprofile.domain.UserRepository;
import com.oxygensened.userprofile.domain.exception.TechnologyIsObtainedByUserException;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TechnologyService {
    private final UserRepository userRepository;

    public TechnologyService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public TechnologyView addTechnology(Long userId, TechnologyRequest request) {
        var user = userRepository.findById(userId)
                                 .orElseThrow(() -> UserNotFoundException.withId(userId));

        if (user.technologies().contains(request.name())) {
            throw new TechnologyIsObtainedByUserException("Technology %s is already obtained by user".formatted(request.name()));
        }

        user.addTechnology(request.name());
        userRepository.save(user);

        return new TechnologyView(request.name());
    }

    public void deleteTechnology(Long userId, TechnologyRequest request) {
        var user = userRepository.findById(userId)
                                 .orElseThrow(() -> UserNotFoundException.withId(userId));

        if (!user.technologies().contains(request.name())) {
            throw new TechnologyIsObtainedByUserException("Technology with id %s is not obtained by user".formatted(request.name()));
        }

        user.removeTechnology(request.name());
        userRepository.save(user);
    }
}
