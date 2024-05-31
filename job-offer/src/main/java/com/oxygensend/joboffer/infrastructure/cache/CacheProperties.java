package com.oxygensend.joboffer.infrastructure.cache;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("job-offers.cache")
public record CacheProperties(@NotNull Duration forYouCacheTtl,
                              @NotNull Duration jobOffersCacheTtl,
                              @NotNull Duration globalCacheTtl) {
}
