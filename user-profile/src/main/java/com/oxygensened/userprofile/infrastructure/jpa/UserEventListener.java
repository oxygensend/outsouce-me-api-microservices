package com.oxygensened.userprofile.infrastructure.jpa;

import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.event.DomainEventPublisher;
import com.oxygensened.userprofile.domain.event.UserDetailsDataEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

@Component
final class UserEventListener implements PostUpdateEventListener, PostInsertEventListener {

    private static final List<String> listenToFields = List.of("name", "surname", "phoneNumber", "imageName",
                                                               "imageNameSmall", "activeJobPosition", "accountType", "address",
                                                               "experience", "technologies");
    private final DomainEventPublisher domainEventPublisher;

    UserEventListener(DomainEventPublisher domainEventPublisher) {
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public void onPostInsert(PostInsertEvent event) {
        if (event.getEntity() instanceof User user) {
            domainEventPublisher.publish(new UserDetailsDataEvent(user.id(), user.toMap()));
        }
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
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
                domainEventPublisher.publish(new UserDetailsDataEvent(user.id(), changes));
            }
        }
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister persister) {
        return false;
    }
}
