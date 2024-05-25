package com.oxygensend.opinions.infrastructure.kafka

import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.apache.kafka.common.security.auth.SecurityProtocol
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties("kafka.producer")
data class KafkaProducerProperties(
    @field:NotNull val bootstrapServers: String?,
    @field:NotNull val securityProtocol: SecurityProtocol?,
    val saslJaasConfig: String?,
    val saslMechanism: String?,
    @field:Valid val ssl: KafkaSsl?,
    @field:NotNull val retries: Int?,
    @field:NotNull val retryBackoffInMs: Int?
) {
    @Validated
    data class KafkaSsl(
        @field:NotNull val enabled: Boolean?,
        val keyStore: String?,
        val keyStorePassword: String?,
        val keyStoreType: String?,
        val keyPassword: String?,
        val trustStore: String?,
        val trustStorePassword: String?,
        val trustStoreType: String?
    )
}
