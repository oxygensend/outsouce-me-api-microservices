package com.oxygensend.opinions.domain.aggregate

import org.bson.types.ObjectId

data class CommentDto(
    val id: ObjectId,
    val author: AuthorDto,
    val text: String
)
