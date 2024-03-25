package com.oxygensened.userprofile.config.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "services")
public record ServiceProperties(@Valid Auth auth, @Valid Notifications notifications) {
    public record Auth(@NotEmpty String url) {
    }

    public record Notifications(@NotEmpty String externalTopic, @NotEmpty String login) {

    }
}
