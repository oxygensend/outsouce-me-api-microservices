package com.oxygensened.userprofile.application.auth.event;

import com.oxygensened.userprofile.application.auth.AuthRepository;
import com.oxygensened.userprofile.application.notifications.NotificationsService;
import com.oxygensened.userprofile.domain.exception.NotSupportedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.oxygensened.userprofile.application.auth.event.UserRegisteredEvent.AccountActivation.VERIFY_EMAIL;

@Component
final class UserRegisteredEventConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegisteredEventConsumer.class);
    private final AuthRepository authRepository;
    private final NotificationsService notificationsService;

    UserRegisteredEventConsumer(AuthRepository authRepository, NotificationsService notificationsService) {
        this.authRepository = authRepository;
        this.notificationsService = notificationsService;
    }

    @KafkaListener(id = "userRegistered", topics = "${services.auth.userRegisteredTopic}",
            containerFactory = "userRegisteredEventConcurrentKafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, UserRegisteredEvent> record) {
        var event = record.value();

        if (event.accountActivation() != VERIFY_EMAIL) {
            throw new NotSupportedException("Only email verification is supported");
        }

        String token = authRepository.generateEmailVerificationToken(event.userId());
        notificationsService.sendEmailVerificationLink(event.email(), event.businessId(), token);

        LOGGER.info("Email verification link sent to user {}", event.email());
    }
}
