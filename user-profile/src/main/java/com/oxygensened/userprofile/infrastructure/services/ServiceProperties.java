package com.oxygensened.userprofile.infrastructure.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "services")
public record ServiceProperties(@Valid Auth auth,
                                @Valid Notifications notifications,
                                @Valid StaticData staticData,
                                @Valid JobOffers jobOffers) {
    public record Auth(@NotEmpty String url, @NotEmpty String userRegisteredTopic) {
    }

    public record Notifications(@NotEmpty String externalTopic, @NotEmpty String login) {

    }

    public record JobOffers(@NotEmpty String jobOfferDataTopic) {
    }

    public record StaticData(@NotEmpty String url) {
    }
}
