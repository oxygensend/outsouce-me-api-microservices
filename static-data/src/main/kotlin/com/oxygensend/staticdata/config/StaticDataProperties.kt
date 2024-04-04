package com.oxygensend.staticdata.config

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import java.io.File

@Validated
@ConfigurationProperties(value = "static-data")
data class StaticDataProperties(
    @NotNull val technologyCsvFile: File?,
    @NotEmpty val postalCodeDataUrl: String?,
    @NotEmpty val openStreetMapUrl: String?
)
