package com.oxygensened.userprofile.infrastructure.kafka;

import com.oxygensened.userprofile.infrastructure.UserProfileProperties;
import com.oxygensened.userprofile.domain.DomainEvent;
import com.oxygensened.userprofile.domain.DomainEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
final class KafkaEventPublisher implements DomainEventPublisher {
    private final KafkaTemplate<String, DomainEvent> kafkaTemplate;
    private final UserProfileProperties userProfileProperties;

    KafkaEventPublisher(KafkaTemplate<String, DomainEvent> kafkaTemplate, UserProfileProperties userProfileProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.userProfileProperties = userProfileProperties;
    }

    @Override
    public void publish(DomainEvent domainEvent) {
        kafkaTemplate.send(userProfileProperties.topics().get(domainEvent.topic()), domainEvent.key(), domainEvent);
    }
}
