package com.oxygensend.joboffer.context.properties;

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
