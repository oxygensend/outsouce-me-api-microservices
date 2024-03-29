package com.oxygensend.staticdata.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document("addresses")
data class Address(
    @Id val id: UUID,
    val city: String,
    val postCodes: List<String>,
    val lon: Double,
    val lat: Double,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
