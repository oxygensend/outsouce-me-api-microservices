package com.oxygensend.staticdata.config

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import java.io.File

@ConfigurationProperties(value = "static-data")
data class StaticDataProperties(
    @NotNull val technologyCsvFile: File?,
    @NotEmpty val postalCodeDataUrl: String?,
    @NotEmpty val openStreetMapUrl: String?
)
