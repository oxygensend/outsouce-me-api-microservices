package com.oxygensend.opinions.application.event

data class UserDetailsDataEvent(
    val id: String,
    val fields: Map<String, Any>
)
