package com.oxygensend.opinions.domain.event

data class UserOpinionsDetailsEvent(
    val id: String,
    val opinionsCount: Int,
    val opinionsRate: Double
) : DomainEvent {

    override fun topic(): Topics {
        return Topics.USER_OPINIONS_DATA
    }

    override fun key(): String {
        return id
    }

}