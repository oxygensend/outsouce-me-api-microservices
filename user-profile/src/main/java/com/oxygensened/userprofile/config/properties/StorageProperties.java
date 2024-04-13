package com.oxygensened.userprofile.config.properties;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "user-profile.storage")
public record StorageProperties(@NotEmpty String rootLocation,
                                @NotEmpty String thumbnailDir) {

}
