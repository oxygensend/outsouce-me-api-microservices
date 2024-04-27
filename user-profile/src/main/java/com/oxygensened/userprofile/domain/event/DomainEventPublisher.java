package com.oxygensened.userprofile.domain.event;

public interface DomainEventPublisher {
    void publish(DomainEvent domainEvent);
}
