package com.oxygensend.joboffer.context.user.event;

import com.oxygensend.joboffer.domain.exception.NoSuchUserException;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserOpinionsDetailsEventConsumer {

    private final UserRepository userRepository;

    UserOpinionsDetailsEventConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    @KafkaListener(id = "userOpinionsDetails", topics = "${services.opinions.userOpinionsDetailsTopic}",
            containerFactory = "userOpinionsDetailsEventConcurrentKafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, UserOpinionsDetailsEvent> record) {
        var event = record.value();
        var user = userRepository.findById(event.id())
                                 .orElseThrow(() -> new NoSuchUserException("Event UserOpinionsDetails ignored, user %s not found"));


        user.setOpinionsCount(event.opinionsCount());
        user.setOpinionsRate(event.opinionsRate());
    }
}
