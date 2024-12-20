package com.oxygensend.opinions.domain.aggregate

import org.bson.types.ObjectId

data class AggregatedOpinionDto(
    val id: ObjectId,
    val author: AuthorDto,
    val receiver: String,
    val text: String?,
    val scale: Int,
    val likes: Int,
    val liked: Boolean,
    val numberOfComments: Int
)
