package com.oxygensened.userprofile.infrastructure.elasticsearch;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "user-profile.elasticsearch")
public record ElasticSearchProperties(@NotNull BootstrapType boostrapType) {
}
