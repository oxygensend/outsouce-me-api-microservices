package com.oxygensend.opinions.infrastructure.kafka

import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.slf4j.LoggerFactory
import org.springframework.kafka.support.ProducerListener
import org.springframework.lang.Nullable
import java.lang.Exception

internal class KafkaProducerListener<K, V> : ProducerListener<K, V> {
    override fun onSuccess(producerRecord: ProducerRecord<K, V>, recordMetadata: RecordMetadata) {
        LOGGER.info("Event with key: {} transferred successfully to topic: {}", producerRecord.key(), producerRecord.topic())
    }

    override fun onError(producerRecord: ProducerRecord<K, V>, @Nullable recordMetadata: RecordMetadata?, exception: Exception?) {
        LOGGER.info(
            "Event with key: ${producerRecord.key()} wasn't transferred to topic: ${producerRecord.key()}"
        )
        throw RuntimeException("Message sending failure ${producerRecord.key()} to ${producerRecord.topic()}")
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(KafkaProducerListener::class.java)
    }
}
