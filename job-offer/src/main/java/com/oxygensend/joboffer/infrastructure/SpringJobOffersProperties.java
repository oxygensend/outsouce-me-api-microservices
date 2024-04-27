package com.oxygensend.joboffer.infrastructure;

import com.oxygensend.joboffer.context.JobOffersProperties;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

// TODO add cron validator
@Validated
@ConfigurationProperties(prefix = "job-offers")
record SpringJobOffersProperties(@NotNull
                                 String serviceId,
                                 @NotNull
                                 String userThumbnailServerUrl,
                                 @NotNull
                                 String checkJobOfferExpirationCron) implements JobOffersProperties {

}
