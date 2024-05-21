package com.oxygensend.joboffer.infrastructure;

import com.oxygensend.joboffer.context.properties.InternalMessageProperties;
import com.oxygensend.joboffer.context.properties.JobOffersProperties;
import com.oxygensend.joboffer.context.properties.MailMessageProperties;
import com.oxygensend.joboffer.context.properties.NotificationsProperties;
import com.oxygensend.joboffer.domain.event.Topics;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
                                 Map<Topics, String> topics,
                                 @Valid NotificationsProperties notifications) implements JobOffersProperties {

    public record SpringNotificationProperties(@NotNull
                                               @Valid
                                               SpringMailMessageProperties jobOfferApplicationEmail,
                                               @NotNull
                                               @Valid
                                               SpringInternalMessageProperties jobOfferApplicationInternalMessage
                                               ) implements NotificationsProperties {


    }

    public record SpringMailMessageProperties(@NotEmpty String subject,
                                              @NotEmpty String body) implements MailMessageProperties {

    }

    public record SpringInternalMessageProperties(@NotEmpty String body) implements InternalMessageProperties {
    }
}
