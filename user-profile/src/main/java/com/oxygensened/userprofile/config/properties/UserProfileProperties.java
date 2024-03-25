package com.oxygensened.userprofile.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "user-profile")
public record UserProfileProperties(String serviceId) {
}
