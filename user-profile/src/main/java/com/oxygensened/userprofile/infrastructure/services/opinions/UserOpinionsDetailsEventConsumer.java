package com.oxygensened.userprofile.infrastructure.services.opinions;

import com.oxygensened.userprofile.application.cache.event.ClearDetailsCacheEvent;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserOpinionsDetailsEventConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserOpinionsDetailsEventConsumer.class);

    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    UserOpinionsDetailsEventConsumer(UserRepository userRepository,
                                     ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Transactional
    @KafkaListener(id = "userOpinionsDetails", topics = "${services.opinions.userOpinionsDetailsTopic}",
        containerFactory = "userOpinionsDetailsEventConcurrentKafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, UserOpinionsDetailsEvent> record) {
        LOGGER.info("Consuming user opinions details for user {}", record.key());

        var event = record.value();
        var user = userRepository.findById(Long.valueOf(event.id()))
                                 .orElseThrow(() -> new UserNotFoundException(
                                     "Event UserOpinionsDetails ignored, user %s not found"));


        user.setOpinionsCount(event.opinionsCount());
        user.setOpinionsRate(event.opinionsRate());
        applicationEventPublisher.publishEvent(ClearDetailsCacheEvent.user(user.id()));
    }
}
