package com.oxygensend.joboffer.infrastructure.elasticsearch;

import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@EnableConfigurationProperties(ElasticSearchProperties.class)
@Configuration
public class ElasticSearchConfiguration {

    @Bean
    public ElasticSearchMapper elasticSearchMapper() {
        return new ElasticSearchMapper();
    }

    @Bean
    ElasticSearchBootstrapInitializer elasticSearchBootstrapInitializer(ElasticsearchOperations elasticsearchOperations, JobOfferRepository jobOfferRepository,
                                                                        ElasticSearchMapper elasticsearchMapper, ElasticSearchProperties properties) {
        return new ElasticSearchBootstrapInitializer(elasticsearchOperations, jobOfferRepository, elasticsearchMapper, properties.boostrapType());
    }
}
