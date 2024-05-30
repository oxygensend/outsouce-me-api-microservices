package com.oxygensend.joboffer.infrastructure.services.staticdata;

import com.oxygensend.commonspring.feign.ClientFactory;
import com.oxygensend.joboffer.application.technology.TechnologyRepository;
import com.oxygensend.joboffer.infrastructure.services.ServiceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ServiceProperties.class)
public class StaticDataConfiguration {

    @Bean
    StaticDataClient staticDataClient(ClientFactory clientFactory, ServiceProperties serviceProperties) {
        return clientFactory.create(serviceProperties.staticData().url(), StaticDataClient.class, builder -> {
        });
    }

    @Bean
    TechnologyRepository technologyRepository(StaticDataClient staticDataClient) {
        return new StaticDataTechnologyRepository(staticDataClient);
    }
}
