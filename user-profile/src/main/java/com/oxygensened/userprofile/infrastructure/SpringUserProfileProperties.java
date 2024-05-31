package com.oxygensened.userprofile.infrastructure;

import com.oxygensened.userprofile.application.properties.InternalMessageProperties;
import com.oxygensened.userprofile.application.properties.MailMessageProperties;
import com.oxygensened.userprofile.application.properties.NotificationsProperties;
import com.oxygensened.userprofile.application.properties.UserProfileProperties;
import com.oxygensened.userprofile.domain.event.Topics;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "user-profile")
record SpringUserProfileProperties(String serviceId,
                                   Map<Topics, String> topics,
                                   @NotEmpty String thumbnailServerUrl,
                                   @NotEmpty String plUniversitiesSourceUrl,
                                   @NotEmpty String developersPopularityRateRecalculationCron,
                                   @NotEmpty String emailVerificationFrontendUrl,
                                   @NotEmpty String passwordResetFrontendUrl,
                                   @Valid SpringNotificationProperties notifications,
                                   @NotEmpty String defaultThumbnail
) implements UserProfileProperties {
    private final static Integer TOPICS_SIZE = 1;

    public SpringUserProfileProperties {
        if (topics.size() != TOPICS_SIZE) {
            throw new IllegalArgumentException("Invalid topics configuration");
        }
    }

    public record SpringNotificationProperties(@NotNull
                                               @Valid
                                               SpringMailMessageProperties emailVerificationEmail,
                                               @NotNull
                                               @Valid
                                               SpringMailMessageProperties passwordResetEmail,
                                               @NotNull
                                               @Valid
                                               SpringMailMessageProperties welcomeMessageEmail,
                                               @NotNull
                                               @Valid
                                               SpringInternalMessageProperties welcomeMessageInternal) implements NotificationsProperties {


    }

    public record SpringMailMessageProperties(@NotEmpty String subject,
                                              @NotEmpty String body) implements MailMessageProperties {

    }

    public record SpringInternalMessageProperties(@NotEmpty String body) implements InternalMessageProperties {
    }

}
