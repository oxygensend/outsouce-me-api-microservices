package com.oxygensened.userprofile.infrastructure.elasticsearch;

import com.oxygensened.userprofile.application.properties.UserProfileProperties;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@Configuration
@EnableConfigurationProperties(ElasticSearchProperties.class)
public class ElasticSearchConfiguration {


    @Bean
    ElasticSearchMapper elasticsearchMapper(UserProfileProperties userProfileProperties) {
        return new ElasticSearchMapper(userProfileProperties.thumbnailServerUrl());
    }

    @Bean
    ElasticSearchBootstrapInitializer elasticSearchInitializer(ElasticsearchOperations elasticsearchOperations, UserRepository userRepository,
                                                               ElasticSearchMapper elasticsearchMapper, ElasticSearchProperties elasticSearchProperties) {
        return new ElasticSearchBootstrapInitializer(elasticsearchOperations, userRepository, elasticsearchMapper, elasticSearchProperties.boostrapType());
    }
}
