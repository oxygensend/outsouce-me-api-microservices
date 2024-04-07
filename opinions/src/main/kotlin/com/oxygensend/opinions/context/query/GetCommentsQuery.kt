package com.oxygensend.opinions.context.query

import com.oxygensend.opinions.context.dto.CommentSort
import org.bson.types.ObjectId
import org.springframework.data.domain.Pageable

data class GetCommentsQuery(
    val opinionId: ObjectId,
    val pageable: Pageable,
    val sort: CommentSort
)
