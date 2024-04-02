package com.oxygensend.staticdata.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("addresses")

class Address(
    @Id
    val id: String
) {
    lateinit var city: String
    var postalCodes: Set<String> = emptySet()
    var lon: Double? = null
    var lat: Double? = null
    var createdAt: LocalDateTime = LocalDateTime.now()
    var updatedAt: LocalDateTime = LocalDateTime.now()

}