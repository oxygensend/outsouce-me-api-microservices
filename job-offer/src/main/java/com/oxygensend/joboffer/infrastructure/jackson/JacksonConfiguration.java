package com.oxygensend.joboffer.infrastructure.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfiguration {

    @Bean
    Jackson2ObjectMapperBuilder objectMapperBuilder() {
        var builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.ALWAYS)
               .modulesToInstall(new JsonNullableModule());
        return builder;
    }
}
