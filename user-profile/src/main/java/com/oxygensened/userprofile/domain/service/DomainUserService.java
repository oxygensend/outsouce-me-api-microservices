package com.oxygensened.userprofile.domain.service;

import com.oxygensened.userprofile.domain.User;
import com.oxygensened.userprofile.domain.exception.TechnologyIsObtainedByUserException;
import org.springframework.stereotype.Service;

@Service
public class DomainUserService {

    public void addTechnology(User user, String technology) {
        if (user.technologies().contains(technology)) {
            throw new TechnologyIsObtainedByUserException("Technology %s is already obtained by user".formatted(technology));
        }

        user.addTechnology(technology);
    }

    public void deleteTechnology(User user, String technology) {
        if (!user.technologies().contains(technology)) {
            throw new TechnologyIsObtainedByUserException("Technology with id %s is not obtained by user".formatted(technology));
        }

        user.removeTechnology(technology);
    }
}
