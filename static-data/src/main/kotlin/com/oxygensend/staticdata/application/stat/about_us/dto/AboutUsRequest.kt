package com.oxygensend.staticdata.application.stat.about_us.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class AboutUsRequest(
    @field:NotEmpty
    @field:Size(max = 255)
    val title: String?,
    @field:NotEmpty
    @field:Size(max = 2048)
    val description: String?,
    val enabled: Boolean
)
