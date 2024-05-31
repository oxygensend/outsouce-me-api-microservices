package com.oxygensened.userprofile.context.properties;

import com.oxygensened.userprofile.domain.event.Topics;
import java.util.Map;

public interface UserProfileProperties {
    String serviceId();

    Map<Topics, String> topics();

    String thumbnailServerUrl();

    String plUniversitiesSourceUrl();

    String developersPopularityRateRecalculationCron();

    String emailVerificationFrontendUrl();

    String passwordResetFrontendUrl();

    NotificationsProperties notifications();

    String defaultThumbnail();
}
