package com.oxygensend.opinions.context.dto

import org.bson.types.ObjectId

data class CommentDto(
    val id: ObjectId,
    val author: AuthorDto,
    val text: String
)
