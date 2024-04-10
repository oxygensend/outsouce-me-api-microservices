package com.oxygensend.opinions.config

import com.oxygensend.commons_jdk.exception.ApiException
import com.oxygensend.opinions.context.event.UserDetailsDataEvent
import com.oxygensend.opinions.domain.exception.ServiceUnavailableException
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.config.SslConfigs
import org.apache.kafka.common.security.auth.SecurityProtocol
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.CommonErrorHandler
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.serializer.DeserializationException
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.util.backoff.BackOff
import org.springframework.util.backoff.FixedBackOff
import java.util.*

@EnableConfigurationProperties(KafkaConsumerProperties::class)
@Configuration
class KafkaConsumerConfiguration(private val kafkaProperties: KafkaConsumerProperties) {
    @Bean
    fun userDetailsDataEventConsumerFactory(): ConsumerFactory<String, UserDetailsDataEvent> {
        return DefaultKafkaConsumerFactory(
            consumerProperties(), StringDeserializer(),
            errorHandlingDeserializer(UserDetailsDataEvent::class.java)
        )
    }

    @Bean
    fun userDetailsDataEventConcurrentKafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, UserDetailsDataEvent>
    ): ConcurrentKafkaListenerContainerFactory<String, UserDetailsDataEvent> {
        val containerFactory: ConcurrentKafkaListenerContainerFactory<String, UserDetailsDataEvent> =
            ConcurrentKafkaListenerContainerFactory<String, UserDetailsDataEvent>()
        containerFactory.consumerFactory = consumerFactory
        containerFactory.setCommonErrorHandler(errorHandler())
        return containerFactory
    }

    private fun consumerProperties(): Map<String, Any?> {
        val configProps: MutableMap<String, Any?> = HashMap()
        configProps[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers
        configProps[ConsumerConfig.GROUP_ID_CONFIG] = kafkaProperties.applicationId + UUID.randomUUID()
        configProps[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false
        configProps[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = kafkaProperties.autoOffsetReset
        configProps[ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG] = kafkaProperties.maxPollIntervalMs
        configProps[ConsumerConfig.RETRY_BACKOFF_MS_CONFIG] = kafkaProperties.retryBackoffMs
        configProps[ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG] = kafkaProperties.requestTimeoutMs
        configProps[ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG] = kafkaProperties.connectionsMaxIdleMs
        configProps.putAll(secureConfigProperties())
        return configProps
    }

    private fun secureConfigProperties(): Map<String, Any?> {
        val secureProps: MutableMap<String, Any?> = HashMap()
        val securityProtocol = kafkaProperties.securityProtocol
        val kafkaSslSettings = kafkaProperties.ssl
        if (!kafkaSslSettings.enabled) {
            return secureProps
        }
        secureProps[CommonClientConfigs.SECURITY_PROTOCOL_CONFIG] = securityProtocol!!.name
        if (securityProtocol == SecurityProtocol.SSL || securityProtocol == SecurityProtocol.SASL_SSL) {
            secureProps[SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG] = kafkaSslSettings.trustStore
            secureProps[SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG] = kafkaSslSettings.trustStorePassword
            secureProps[SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG] = kafkaSslSettings.trustStoreType
            secureProps[SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG] = kafkaSslSettings.keyStore
            secureProps[SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG] = kafkaSslSettings.keyStorePassword
            secureProps[SslConfigs.SSL_KEYSTORE_TYPE_CONFIG] = kafkaSslSettings.keyStoreType
            secureProps[SslConfigs.SSL_KEY_PASSWORD_CONFIG] = kafkaSslSettings.keyPassword
        }
        if (securityProtocol == SecurityProtocol.SASL_SSL) {
            secureProps[SaslConfigs.SASL_JAAS_CONFIG] = kafkaProperties.saslJaasConfig
            secureProps[SaslConfigs.SASL_MECHANISM] = kafkaProperties.saslMechanism
        }
        return secureProps
    }

    private fun errorHandler(): CommonErrorHandler {
        val errorHandler =
            DefaultErrorHandler { consumerRecord: ConsumerRecord<*, *>, exception: Exception -> consumerRecordRecoverer(consumerRecord, exception) }
        errorHandler.addRetryableExceptions(ServiceUnavailableException::class.java, ApiException::class.java)
        errorHandler.setBackOffFunction { _, exception -> backOffConfig(exception) }
        return errorHandler
    }

    private fun backOffConfig(exception: Exception): BackOff {
        val retry = kafkaProperties.retry
        return when (exception) {
            is ApiException -> FixedBackOff(retry.backOffPeriod, retry.maxRetries)
            is ServiceUnavailableException -> FixedBackOff(retry.backoffPeriodServiceUnavailable, FixedBackOff.UNLIMITED_ATTEMPTS)
            else -> FixedBackOff(0, 0)
        }
    }

    private fun consumerRecordRecoverer(consumerRecord: ConsumerRecord<*, *>, exception: Exception) {
        if (isDeserializationException(exception)) {
            val deserializationException: DeserializationException = exception as DeserializationException
            val brokenMessage = String(deserializationException.data)
            LOGGER.info(
                "Skipping record with topic: {}, partition: {}, offset: {}, broken message: {}", consumerRecord.topic(),
                consumerRecord.partition(), consumerRecord.offset(), brokenMessage
            )
        } else {
            LOGGER.info(
                "Consumer record exception - topic: {}, partition: {}, offset: {}, cause: {}", consumerRecord.topic(),
                consumerRecord.partition(), consumerRecord.offset(), exception.message
            )
        }
    }

    private fun isDeserializationException(exception: Exception): Boolean {
        return exception is DeserializationException
    }

    private fun <T> errorHandlingDeserializer(clazz: Class<T>): ErrorHandlingDeserializer<T> {
        return ErrorHandlingDeserializer(JsonDeserializer(clazz, false))
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(KafkaConsumerConfiguration::class.java)
    }
}
