package com.oxygensend.joboffer.infrastructure.elasticsearch;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "job-offers.elasticsearch")
public record ElasticSearchProperties(@NotNull BootstrapType boostrapType) {
}
