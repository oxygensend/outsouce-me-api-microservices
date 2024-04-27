package com.oxygensend.joboffer.infrastructure;

import com.oxygensend.joboffer.context.JobOffersProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "job-offers")
record SpringJobOffersProperties(String serviceId,
                                 String userThumbnailServerUrl) implements JobOffersProperties {

}
