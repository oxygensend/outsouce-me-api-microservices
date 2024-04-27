package com.oxygensend.opinions.domain.aggregate.filter

import org.bson.types.ObjectId

data class CommentsFilter(
    val opinionId: ObjectId,
    val sort: CommentSort
)