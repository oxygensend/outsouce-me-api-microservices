package com.oxygensend.staticdata.application.technology

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class TechnologyRequest(
    @field:NotEmpty val name: String?,
    @field:NotNull val featured: Boolean?
)
