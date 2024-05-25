package com.oxygensend.opinions.infrastructure.kafka

import com.oxygensend.opinions.context.OpinionsProperties
import com.oxygensend.opinions.domain.event.DomainEvent
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.TopicDescription
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.KafkaFuture
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.config.SslConfigs
import org.apache.kafka.common.errors.InvalidConfigurationException
import org.apache.kafka.common.security.auth.SecurityProtocol
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonSerializer
import java.util.concurrent.ExecutionException

@EnableConfigurationProperties(KafkaProducerProperties::class)
@Configuration
class KafkaProducerConfiguration internal constructor(
    private val kafkaProperties: KafkaProducerProperties,
    private val opinionsProperties: OpinionsProperties
) {
    init {
        verifyTopicsExistence()
    }

    @Bean
    fun userOpinionsDataKafkaTemplate(): KafkaTemplate<String, DomainEvent> {
        val producerFactory = DefaultKafkaProducerFactory<String, DomainEvent>(configProperties())
        val kafkaTemplate = KafkaTemplate(producerFactory)
        kafkaTemplate.setProducerListener(KafkaProducerListener())
        return kafkaTemplate
    }

    @Bean
    internal fun kafkaEventPublisher(kafkaTemplate: KafkaTemplate<String, DomainEvent>): KafkaEventPublisher {
        return KafkaEventPublisher(kafkaTemplate, opinionsProperties.topics)
    }

    @Bean
    internal fun kafkaProducerListener(): KafkaProducerListener<String, DomainEvent> {
        return KafkaProducerListener()
    }

    private fun configProperties(): Map<String, Any> {
        val configProperties: MutableMap<String, Any> = HashMap()
        configProperties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers!!
        configProperties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] =
            StringSerializer::class.java
        configProperties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        configProperties[ProducerConfig.RETRIES_CONFIG] = kafkaProperties.retries!!
        configProperties[ProducerConfig.RETRY_BACKOFF_MS_CONFIG] = kafkaProperties.retryBackoffInMs!!
        configProperties[ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION] = 1
        if (kafkaProperties.ssl?.enabled == true) {
            configProperties.putAll(secureConfigProperties())
        }
        return configProperties
    }

    private fun secureConfigProperties(): MutableMap<String, Any> {
        val secureProps: MutableMap<String, Any> = HashMap()
        if (kafkaProperties.securityProtocol == SecurityProtocol.SSL || kafkaProperties.securityProtocol == SecurityProtocol.SASL_SSL) {
            secureProps[CommonClientConfigs.SECURITY_PROTOCOL_CONFIG] = kafkaProperties.securityProtocol!!.name
            val sslProps = kafkaProperties.ssl!!
            secureProps[SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG] = sslProps.trustStore!!
            secureProps[SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG] = sslProps.trustStorePassword!!
            secureProps[SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG] = sslProps.trustStoreType!!
            secureProps[SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG] = sslProps.keyStore!!
            secureProps[SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG] = sslProps.keyStorePassword!!
            secureProps[SslConfigs.SSL_KEYSTORE_TYPE_CONFIG] = sslProps.keyStoreType!!
            secureProps[SslConfigs.SSL_KEY_PASSWORD_CONFIG] = sslProps.keyPassword!!
        }
        if (kafkaProperties.securityProtocol == SecurityProtocol.SASL_SSL) {
            secureProps[SaslConfigs.SASL_JAAS_CONFIG] = kafkaProperties.saslJaasConfig!!
            secureProps[SaslConfigs.SASL_MECHANISM] = kafkaProperties.saslMechanism!!
        }
        return secureProps
    }

    private fun verifyTopicsExistence() {
        val props = secureConfigProperties()
        props[CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers!!
        AdminClient.create(props).use { adminClient ->
            val topics = allTopics
            val describeTopicsResult = adminClient.describeTopics(topics)
            describeTopicsResult.topicNameValues().forEach { (key: String, value: KafkaFuture<TopicDescription?>) ->
                try {
                    value.get()
                } catch (exception: InterruptedException) {
                    LOGGER.error("Error during topic description retrieval", exception)
                    throw InvalidConfigurationException("Failed to obtain topic description: $key")
                } catch (exception: ExecutionException) {
                    LOGGER.error("Error during topic description retrieval", exception)
                    throw InvalidConfigurationException("Failed to obtain topic description: $key")
                }
            }
        }
    }

    private val allTopics: Set<String>
        get() = HashSet(opinionsProperties.topics.values)

    companion object {
        private val LOGGER = LoggerFactory.getLogger(KafkaProducerConfiguration::class.java)
    }
}
