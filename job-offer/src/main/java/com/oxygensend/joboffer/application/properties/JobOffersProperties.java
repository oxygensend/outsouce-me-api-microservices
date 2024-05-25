package com.oxygensend.joboffer.application.properties;

import com.oxygensend.joboffer.domain.event.Topics;
import java.util.Map;

public interface JobOffersProperties {

    String serviceId();
    String userThumbnailServerUrl();
    String checkJobOfferExpirationCron();
    String recalculateJobOffersPopularityRateCron();
    Map<Topics, String> topics();

    NotificationsProperties notifications();
}
