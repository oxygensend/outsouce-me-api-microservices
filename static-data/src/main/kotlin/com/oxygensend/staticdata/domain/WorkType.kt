package com.oxygensend.staticdata.domain

enum class WorkType(private val displayName: String) {
    REMOTE("Remote"),
    ONSITE("Onsite"),
    HYBRID("Hybrid"),
    OFFICE("Office"),
    NEGOTIABLE("Negotiable"),
}