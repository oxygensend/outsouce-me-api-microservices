package com.oxygensened.userprofile.config;

import com.oxygensened.userprofile.config.properties.KafkaConsumerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(KafkaConsumerProperties.class)
@Configuration
public class KafkaConsumerConfiguration {
}
