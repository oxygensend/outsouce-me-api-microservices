package com.oxygensened.userprofile.infrastructure.services.opinions;

import com.oxygensened.userprofile.application.cache.event.ClearDetailsCacheEvent;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserOpinionsDetailsEventConsumer {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    UserOpinionsDetailsEventConsumer(UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Transactional
    @KafkaListener(id = "userOpinionsDetails", topics = "${services.opinions.userOpinionsDetailsTopic}",
            containerFactory = "userOpinionsDetailsEventConcurrentKafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, UserOpinionsDetailsEvent> record) {
        var event = record.value();
        var user = userRepository.findById(Long.valueOf(event.id()))
                                 .orElseThrow(() -> new UserNotFoundException("Event UserOpinionsDetails ignored, user %s not found"));


        user.setOpinionsCount(event.opinionsCount());
        user.setOpinionsRate(event.opinionsRate());
        applicationEventPublisher.publishEvent(ClearDetailsCacheEvent.user(user.id()));
    }
}
