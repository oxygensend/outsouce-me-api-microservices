package com.oxygensend.joboffer.infrastructure.storage;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "job-offers.storage")
record StorageProperties(@NotEmpty String rootLocation,
                               @NotEmpty String attachmentsLocation) {

}
