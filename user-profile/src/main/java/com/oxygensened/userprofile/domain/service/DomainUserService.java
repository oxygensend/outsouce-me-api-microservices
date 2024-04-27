package com.oxygensened.userprofile.domain.service;

import com.oxygensened.userprofile.domain.JobPosition;
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

    public void changeActiveJobPosition(User user, JobPosition jobPosition) {
        if (jobPosition.endDate() != null) { // If end date is defined Job should not be marked as active
            return;
        }

        jobPosition.setActive(true);
        user.setActiveJobPosition(jobPosition.name());
    }
}
