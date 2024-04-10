package com.oxygensend.opinions.context.event

import com.oxygensend.opinions.domain.User
import com.oxygensend.opinions.domain.UserRepository
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor

@ExtendWith(MockitoExtension::class)
internal class UserDetailsDataEventConsumerTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var userDetailsDataEventConsumer: UserDetailsDataEventConsumer


    @Test
    fun `should create user from event`() {
        val event = UserDetailsDataEvent(
            id = "1",
            fields = mapOf(
                "name" to "John",
                "surname" to "Doe",
                "thumbnailPath" to "path"
            )
        )
        val record = ConsumerRecord("topic", 0, 0, "key", event)

        Mockito.`when`(userRepository.findById("1")).thenReturn(null)

        userDetailsDataEventConsumer.consume(record)

        val argumentCaptor = argumentCaptor<User>()
        Mockito.verify(userRepository).save(argumentCaptor.capture())
        assertThat(argumentCaptor.firstValue.id).isEqualTo("1")
        assertThat(argumentCaptor.firstValue.fullName).isEqualTo("John Doe")
        assertThat(argumentCaptor.firstValue.thumbnailPath).isEqualTo("path")

    }

    @Test
    fun `should exit method if event does not contain any supported field`() {
        val event = UserDetailsDataEvent(
            id = "1",
            fields = mapOf(
                "unsupported" to "John"
            )
        )
        val record = ConsumerRecord("topic", 0, 0, "key", event)

        userDetailsDataEventConsumer.consume(record)

        Mockito.verify(userRepository, Mockito.never()).save(any())
    }


}
