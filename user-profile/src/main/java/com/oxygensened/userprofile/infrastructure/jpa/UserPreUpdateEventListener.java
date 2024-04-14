package com.oxygensened.userprofile.infrastructure.jpa;

import com.oxygensened.userprofile.domain.DomainEventPublisher;
import com.oxygensened.userprofile.domain.User;
import com.oxygensened.userprofile.domain.UserDetailsDataEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.springframework.stereotype.Component;

@Component
final class UserPreUpdateEventListener implements PreUpdateEventListener {

    private static final List<String> listenToFields = List.of("name", "surname", "phoneNumber", "imageName", "smallImageName", "activeJobPosition");
    private final DomainEventPublisher domainEventPublisher;

    UserPreUpdateEventListener(DomainEventPublisher domainEventPublisher) {
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        if (event.getEntity() instanceof User user) {

            Object[] oldState = event.getOldState();
            Object[] currentState = event.getState();
            String[] propertyNames = event.getPersister().getPropertyNames();
            Map<String, Object> changes = new HashMap<>();
            for (int i = 0; i < oldState.length; i++) {
                if (!Objects.equals(oldState[i], currentState[i]) && listenToFields.contains(propertyNames[i])) {
                    changes.put(propertyNames[i], currentState[i]);
                }
            }

            if (!changes.isEmpty()) {
                domainEventPublisher.publish(new UserDetailsDataEvent(user.id().toString(), changes));
            }
        }
        return false;
    }
}
