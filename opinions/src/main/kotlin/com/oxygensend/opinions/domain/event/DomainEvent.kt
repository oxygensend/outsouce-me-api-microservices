package com.oxygensend.opinions.domain.event

interface DomainEvent {
    fun topic(): Topics
    fun key(): String
}