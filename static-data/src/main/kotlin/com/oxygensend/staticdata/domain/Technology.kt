package com.oxygensend.staticdata.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("technologies")
data class Technology(
    @Id val name: String,
    val featured: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
