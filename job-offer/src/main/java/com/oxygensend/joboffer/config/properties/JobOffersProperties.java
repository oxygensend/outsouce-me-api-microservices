package com.oxygensend.joboffer.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "job-offers")
public record JobOffersProperties(String serviceId,
                                  String userThumbnailServerUrl) {

}
