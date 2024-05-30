package com.oxygensend.staticdata.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("about_us")
data class AboutUs(
    @Id val id: ObjectId,
    val title: String,
    val description: String,
    val imageName: String,
    val enabled: Boolean,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null
)
