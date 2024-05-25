package com.oxygensend.joboffer.infrastructure.kafka;

import com.oxygensend.joboffer.application.properties.JobOffersProperties;
import com.oxygensend.joboffer.domain.event.DomainEvent;
import com.oxygensend.joboffer.domain.event.DomainEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
final class KafkaEventPublisher implements DomainEventPublisher {
    private final KafkaTemplate<String, DomainEvent> kafkaTemplate;
    private final JobOffersProperties jobOffersProperties;

    KafkaEventPublisher(KafkaTemplate<String, DomainEvent> kafkaTemplate, JobOffersProperties jobOffersProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.jobOffersProperties = jobOffersProperties;
    }

    @Override
    public void publish(DomainEvent domainEvent) {
        kafkaTemplate.send(jobOffersProperties.topics().get(domainEvent.topic()), domainEvent.key(), domainEvent);
    }
}
