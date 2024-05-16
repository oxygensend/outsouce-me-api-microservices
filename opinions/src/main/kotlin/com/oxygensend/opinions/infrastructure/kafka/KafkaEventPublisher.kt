package com.oxygensend.opinions.infrastructure.kafka

import com.oxygensend.opinions.domain.event.DomainEvent
import com.oxygensend.opinions.domain.event.DomainEventPublisher
import com.oxygensend.opinions.domain.event.Topics
import org.springframework.kafka.core.KafkaTemplate

internal class KafkaEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, DomainEvent>,
    private val topics: Map<Topics, String>
) : DomainEventPublisher {

    override fun publish(domainEvent: DomainEvent) {
        topics[domainEvent.topic()]?.let { kafkaTemplate.send(it, domainEvent.key(), domainEvent) };
    }
}