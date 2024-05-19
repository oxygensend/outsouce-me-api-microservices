package com.oxygensend.joboffer.infrastructure;

import com.oxygensend.joboffer.context.JobOffersProperties;
import com.oxygensend.joboffer.domain.event.Topics;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
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
                                 String checkJobOfferExpirationCron,
                                 @NotNull
                                 String recalculateJobOffersPopularityRateCron,
                                 @NotNull
                                 Map<Topics, String> topics) implements JobOffersProperties {

}
