package com.oxygensend.opinions.application.event

import com.oxygensend.opinions.domain.User
import com.oxygensend.opinions.domain.UserRepository
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

// Responsible for synchronizing user data with the user service
@Component
internal class UserDetailsDataEventConsumer(val userRepository: UserRepository) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(this::class.java);
        private val SUPPORTED_FIELDS: List<String> = listOf("name", "surname", "imageNameSmall")
    }

    @KafkaListener(
        id = "user-details-data-event-consumer-opinions",
        topics = ["\${kafka.consumer.user-details-data-topic}"],
        containerFactory = "userDetailsDataEventConcurrentKafkaListenerContainerFactory"
    )
    fun consume(record: ConsumerRecord<String, UserDetailsDataEvent>) {
        LOGGER.info("Consuming user details for user {}", record.key())
        val event = record.value()
        if (event.fields.keys.none { it in SUPPORTED_FIELDS }) {
            LOGGER.info("No user user details for user {}, exiting.", record.key());
            return
        }

        var user = userRepository.findById(event.id)
        if (user == null) {
            user = createUserFromEvent(event)
        } else {
            updateUserFromEvent(event, user)
        }

        userRepository.save(user)
    }

    private fun createUserFromEvent(event: UserDetailsDataEvent): User {
        val name = event.fields.getOrDefault("name", "")
        val surname = event.fields.getOrDefault("surname", "")
        val thumbnailPath = event.fields["imageNameSmall"] as? String
        val fullName = "$name $surname"
        return User(id = event.id, internal = false, fullName = fullName, thumbnailPath = thumbnailPath)
    }

    private fun updateUserFromEvent(event: UserDetailsDataEvent, user: User) {
        if (event.fields.containsKey("name") || event.fields.containsKey("surname")) {
            val name = event.fields.getOrDefault("name", user.fullName?.split(" ")?.get(0) ?: "")
            val surname = event.fields.getOrDefault("surname", user.fullName?.split(" ")?.get(1) ?: "")
            user.fullName = "$name $surname"
        }

        if (event.fields.containsKey("imageNameSmall")) {
            user.thumbnailPath = event.fields["imageNameSmall"] as String
        }
    }

}
