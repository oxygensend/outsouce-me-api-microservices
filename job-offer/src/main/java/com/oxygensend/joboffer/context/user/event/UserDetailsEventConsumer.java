package com.oxygensend.joboffer.context.user.event;

import com.oxygensend.joboffer.domain.entity.part.AccountType;
import com.oxygensend.joboffer.domain.entity.User;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
final class UserDetailsEventConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsEventConsumer.class);
    private static final List<String> SUPPORTED_FIELDS = List.of("name", "surname", "email", "imageNameSmall", "activeJobPosition", "accountType");
    private final UserRepository userRepository;

    UserDetailsEventConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(
            id = "user-details-data-event-consumer2",
            topics = "${kafka.consumer.user-details-data-topic}",
            containerFactory = "userDetailsEventConcurrentKafkaListenerContainerFactory"
    )
    void consume(ConsumerRecord<String, UserDetailsEvent> record) {
        LOGGER.info("XX");
        var event = record.value();
        if (event.fields().keySet().stream().noneMatch(SUPPORTED_FIELDS::contains)) {
            return;
        }

        try {
            var user = userRepository.findById(event.id())
                                     .map(object -> updateUserFromEvent(event, object))
                                     .orElseGet(() -> createUserFromEvent(event));
            userRepository.save(user);
        } catch (Exception exception) {
            LOGGER.error("Exception while parsing userDetailsEvent. Event {} doesnt contain required field", record.key(), exception);
            throw exception;
        }

    }

    private User createUserFromEvent(UserDetailsEvent event) {
        var name = (String) getOrThrow(event.fields(), "name");
        var surname = (String) getOrThrow(event.fields(), "surname");
        var email = (String) getOrThrow(event.fields(), "email");
        var thumbnail = (String) event.fields().get("imageNameSmall");
        var activeJobPosition = (String) event.fields().get("activeJobPosition");
        var accountType = AccountType.valueOf((String) event.fields().get("accountType"));

        return new User(event.id(), name, surname, email, thumbnail, activeJobPosition, accountType);
    }

    private User updateUserFromEvent(UserDetailsEvent event, User user) {
        updateIfPresent(event.fields(), "name", user::setName);
        updateIfPresent(event.fields(), "surname", user::setSurname);
        updateIfPresent(event.fields(), "email", user::setEmail);
        updateIfPresent(event.fields(), "imageNameSmall", user::setThumbnail);
        updateIfPresent(event.fields(), "activeJobPosition", user::setActiveJobPosition);
        updateIfPresent(event.fields(), "accountType", user::setActiveJobPosition);

        return user;
    }


    private <T> void updateIfPresent(Map<String, Object> fields, String key, Consumer<T> setter) {
        if (fields.containsKey(key)) {
            setter.accept((T) fields.get(key));
        }
    }

    private Object getOrThrow(Map<String, Object> fields, String key) {
        return Optional.ofNullable(fields.get(key))
                       .orElseThrow(() -> new IllegalArgumentException("No key %s".formatted(key)));
    }


}
