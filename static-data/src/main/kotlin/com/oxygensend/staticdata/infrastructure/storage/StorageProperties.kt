package com.oxygensend.staticdata.infrastructure.storage

import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "storage")
data class StorageProperties(@field:NotEmpty val aboutUsImageDir: String?)
