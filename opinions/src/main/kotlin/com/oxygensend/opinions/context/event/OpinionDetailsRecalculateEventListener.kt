package com.oxygensend.opinions.context.event

import com.oxygensend.opinions.domain.aggregate.OpinionAggregateRepository
import com.oxygensend.opinions.domain.event.DomainEventPublisher
import com.oxygensend.opinions.domain.event.UserOpinionsDetailsEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class OpinionDetailsRecalculateEventListener(
    private val applicationScope: CoroutineScope,
    private val aggregateRepository: OpinionAggregateRepository,
    private val eventPublisher: DomainEventPublisher
) {


    @Transactional
    @TransactionalEventListener
    fun listen(event: OpinionDetailsRecalculateEvent) {
        applicationScope.launch {
            val opinionsDetails = aggregateRepository.getUserOpinionsDetails(event.userId);
            eventPublisher.publish(UserOpinionsDetailsEvent(event.userId, opinionsDetails.opinionsCount, opinionsDetails.opinionsRate));
        }
    }
}