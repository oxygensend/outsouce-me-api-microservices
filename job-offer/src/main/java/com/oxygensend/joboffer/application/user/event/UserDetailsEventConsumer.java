package com.oxygensend.joboffer.application.user.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oxygensend.joboffer.domain.entity.User;
import com.oxygensend.joboffer.domain.entity.part.AccountType;
import com.oxygensend.joboffer.domain.entity.part.Experience;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserDetailsEventConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsEventConsumer.class);
    private static final List<String> SUPPORTED_FIELDS = List.of("name", "surname", "email", "imageNameSmall", "activeJobPosition", "accountType",
                                                                 "address", "technologies", "experience");
    private static final ObjectMapper OM = new ObjectMapper();
    private final UserRepository userRepository;


    UserDetailsEventConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @KafkaListener(
            id = "user-details-data-event-consumer2",
            topics = "${kafka.consumer.user-details-data-topic}",
            containerFactory = "userDetailsEventConcurrentKafkaListenerContainerFactory"
    )
    public void consume(ConsumerRecord<String, UserDetailsEvent> record) {
        LOGGER.info("Consuming user details for user {}", record.key());
        var event = record.value();
        if (event.fields().keySet().stream().noneMatch(SUPPORTED_FIELDS::contains)) {
            LOGGER.info("No user user details for user {}, exiting.", record.key());
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

        var addressField = event.fields().get("address");
        var address = OM.convertValue(addressField, AddressDto.class);
        var longitude = address != null ? address.lon() : null;
        var latitude = address != null ? address.lat() : null;

        var technologiesField = event.fields().get("technologies");
        Set<String> technologies = technologiesField != null ? ((List<String>) technologiesField).stream().collect(Collectors.toSet()) : new HashSet<>();
        var experience = event.fields().get("experience") != null ? Experience.valueOf((String) event.fields().get("experience")): null;

        return new User(event.id(), name, surname, email, thumbnail, activeJobPosition, accountType, longitude, latitude, experience, technologies);
    }

    private User updateUserFromEvent(UserDetailsEvent event, User user) {
        updateIfPresent(event.fields(), "name", user::setName);
        updateIfPresent(event.fields(), "surname", user::setSurname);
        updateIfPresent(event.fields(), "email", user::setEmail);
        updateIfPresent(event.fields(), "imageNameSmall", user::setThumbnail);
        updateIfPresent(event.fields(), "activeJobPosition", user::setActiveJobPosition);
        updateIfPresent(event.fields(), "accountType", user::setActiveJobPosition);
        updateAddress(event.fields(), user);
        updateTechnologies(event.fields(), user);
        updateExperience(event.fields(), user);


        return user;
    }

    private void updateTechnologies(Map<String, Object> fields, User user) {
        if (fields.containsKey("technologies")) {
            var technologies = ((List<String>) fields.get("technologies")).stream().collect(Collectors.toSet());
            user.setTechnologies(technologies);
        }
    }

    private void updateExperience(Map<String, Object> fields, User user) {
        if (fields.containsKey("experience")) {
            user.setExperience(Experience.valueOf((String) fields.get("experience")));
        }
    }

    private void updateAddress(Map<String, Object> fields, User user) {
        if (fields.containsKey("address")) {
            var address = OM.convertValue(fields.get("address"), AddressDto.class);
            user.setLongitude(address.lon());
            user.setLatitude(address.lat());
        }
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
