package com.oxygensend.joboffer.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.oxygensend.joboffer.infrastructure.services.ServiceProperties;
import com.oxygensend.joboffer.infrastructure.storage.StorageProperties;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@EnableConfigurationProperties( {SpringJobOffersProperties.class, ServiceProperties.class, StorageProperties.class})
@Configuration
public class AppConfiguration {
    @Bean
    ObjectMapper objectMapper() {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JsonNullableModule());
        return mapper;
    }

    @Bean
    RestClient restClient(){
        return RestClient.create();
    }

}
