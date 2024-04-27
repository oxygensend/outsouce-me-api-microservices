package com.oxygensened.userprofile.infrastructure.kafka;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("kafka.producer")
public record KafkaProducerProperties(@NotNull String bootstrapServers,
                                      @NotNull SecurityProtocol securityProtocol,
                                      String saslJaasConfig,
                                      String saslMechanism,
                                     @Valid KafkaSsl ssl,
                                      @NotNull Integer retries,
                                      @NotNull Integer retryBackoffInMs,
                                      String stateDir) {

    public record KafkaSsl(@NotNull Boolean enabled,
                           String keyStore,
                           String keyStorePassword,
                           String keyStoreType,
                           String keyPassword,
                           String trustStore,
                           String trustStorePassword,
                           String trustStoreType) {


    }
}
