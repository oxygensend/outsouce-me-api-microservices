package com.oxygensend.opinions.domain.event

interface DomainEventPublisher {

    fun publish(domainEvent: DomainEvent)
}