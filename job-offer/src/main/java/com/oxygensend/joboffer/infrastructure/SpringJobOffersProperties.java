package com.oxygensend.joboffer.infrastructure;

import com.oxygensend.joboffer.application.properties.InternalMessageProperties;
import com.oxygensend.joboffer.application.properties.JobOffersProperties;
import com.oxygensend.joboffer.application.properties.MailMessageProperties;
import com.oxygensend.joboffer.application.properties.NotificationsProperties;
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
                                 @Valid SpringNotificationProperties notifications) implements JobOffersProperties {

    public record SpringNotificationProperties(@NotNull
                                               @Valid
                                               SpringMailMessageProperties jobOfferApplicationEmail,
                                               @NotNull
                                               @Valid
                                               SpringInternalMessageProperties jobOfferApplicationInternalMessage,
                                               @NotNull
                                               @Valid
                                               SpringInternalMessageProperties jobOfferExpiredInternalMessageToPrincipal,
                                               @NotNull
                                               @Valid
                                               SpringMailMessageProperties jobOfferExpiredEmailToPrincipal,
                                               @NotNull
                                               @Valid
                                               SpringInternalMessageProperties jobOfferExpiredInternalMessageToAppliers,
                                               @NotNull
                                               @Valid
                                               SpringMailMessageProperties jobOfferExpiredEmailToAppliers) implements NotificationsProperties {


    }

    public record SpringMailMessageProperties(@NotEmpty String subject,
                                              @NotEmpty String body) implements MailMessageProperties {

    }

    public record SpringInternalMessageProperties(@NotEmpty String body) implements InternalMessageProperties {
    }
}
