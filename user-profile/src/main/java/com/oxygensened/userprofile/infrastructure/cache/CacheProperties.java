package com.oxygensened.userprofile.infrastructure.cache;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("user-profile.cache")
public record CacheProperties(@NotNull Duration forYouCacheTtl,
                              @NotNull Duration developersCacheTtl,
                              @NotNull Duration globalCacheTtl) {
}
