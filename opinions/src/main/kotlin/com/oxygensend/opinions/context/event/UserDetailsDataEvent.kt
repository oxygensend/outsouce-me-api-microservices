package com.oxygensend.opinions.context.event

data class UserDetailsDataEvent(
    val id: String,
    val fields: Map<String, Any>
)
