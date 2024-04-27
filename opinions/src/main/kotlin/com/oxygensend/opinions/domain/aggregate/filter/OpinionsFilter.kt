package com.oxygensend.opinions.domain.aggregate.filter

data class OpinionsFilter(
    val receiver: String,
    val sortField: OpinionSortField,
    val direction: SortDirection
)
