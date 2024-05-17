package com.oxygensend.joboffer.infrastructure.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "services")
public record ServiceProperties(@Valid Notifications notifications,
                                @Valid StaticData staticData) {
    public record Notifications(@NotEmpty String externalTopic, @NotEmpty String login) {

    }

    public record StaticData(@NotEmpty String url) {
    }
}
