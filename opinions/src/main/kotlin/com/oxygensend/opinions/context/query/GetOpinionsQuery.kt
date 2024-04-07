package com.oxygensend.opinions.context.query

import com.oxygensend.opinions.context.dto.OpinionSortField
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

data class GetOpinionsQuery(
    val pageable: Pageable,
    val receiver: String,
    val sortField: OpinionSortField,
    val direction: Sort.Direction
)