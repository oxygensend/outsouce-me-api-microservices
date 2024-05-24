package com.oxygensend.staticdata.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@CompoundIndexes(
    CompoundIndex(
        name = "featured",
        def = "{'featured': -1}"
    ),
)
@Document("technologies")
data class Technology(
    @Id val id: String,
    val name: String,
    var featured: Boolean,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null
)
