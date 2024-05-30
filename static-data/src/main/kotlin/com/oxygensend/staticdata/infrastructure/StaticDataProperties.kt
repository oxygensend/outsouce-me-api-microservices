package com.oxygensend.staticdata.infrastructure

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import java.io.File
import java.time.Duration

@Validated
@ConfigurationProperties(value = "static-data")
data class StaticDataProperties(
    @field:NotNull val technologyCsvFile: File?,
    @field:NotEmpty val postalCodeDataUrl: String?,
    @field:NotEmpty val openStreetMapUrl: String?,
    @field:NotNull @field:Valid val cache: CacheProperties?,
    @field:NotNull val aboutUsImageServerUrl: String?
) {
    data class CacheProperties(
        @field:NotNull val statCacheTtl: Duration,
    )
}
