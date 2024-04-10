package com.oxygensened.userprofile.domain;

public interface DomainEventPublisher {
    void publish(DomainEvent domainEvent);
}
