package com.oxygensend.opinions.context.request

import jakarta.validation.constraints.NotEmpty

data class LikeRequest(
    @field:NotEmpty(message = "opinionId cannot be empty")
    val userId: String
)
