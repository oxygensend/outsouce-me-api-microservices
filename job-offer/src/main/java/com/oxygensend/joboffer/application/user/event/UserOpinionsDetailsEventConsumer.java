package com.oxygensend.joboffer.application.user.event;

import com.oxygensend.joboffer.domain.exception.NoSuchUserException;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserOpinionsDetailsEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserOpinionsDetailsEventConsumer.class);
    private final UserRepository userRepository;

    UserOpinionsDetailsEventConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    @KafkaListener(id = "userOpinionsDetails-job-offer", topics = "${services.opinions.userOpinionsDetailsTopic}",
        containerFactory = "userOpinionsDetailsEventConcurrentKafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, UserOpinionsDetailsEvent> record) {
        LOGGER.info("Consuming user opinions details for user {}", record.key());

        var event = record.value();
        var user = userRepository.findById(event.id())
                                 .orElseThrow(() -> new NoSuchUserException(
                                     "Event UserOpinionsDetails ignored, user %s not found"));


        user.setOpinionsCount(event.opinionsCount());
        user.setOpinionsRate(event.opinionsRate());
    }
}
