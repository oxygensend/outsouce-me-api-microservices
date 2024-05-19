package com.oxygensend.joboffer.domain.event;

public interface DomainEventPublisher {
    void publish(DomainEvent domainEvent);
}
