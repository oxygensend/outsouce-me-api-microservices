package com.oxygensend.staticdata.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document("about_us")
data class AboutUs(
    @Id val id: UUID,
    val title: String,
    val description: String,
    val imageName: String,
    val enabled: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
