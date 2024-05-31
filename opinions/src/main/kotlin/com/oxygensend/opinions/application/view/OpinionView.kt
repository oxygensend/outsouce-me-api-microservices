package com.oxygensend.opinions.application.view

import com.oxygensend.opinions.domain.aggregate.AggregatedOpinionDto
import com.oxygensend.opinions.domain.Opinion
import com.oxygensend.opinions.domain.User

data class OpinionView(
    val id: String,
    val author: UserView,
    val text: String?,
    val scale: Int,
    val likes: Int,
    val liked: Boolean,
    val numberOfComments: Int,
) {

    companion object {
        fun from(opinion: Opinion, author: User) = OpinionView(
            id = opinion.id.toHexString(),
            author = UserView.from(author),
            text = opinion.text,
            scale = opinion.scale,
            likes = opinion.likes.size,
            liked = opinion.likes.contains(opinion.author),
            numberOfComments = opinion.comments.size,
        )

        fun from(opinion: AggregatedOpinionDto) = OpinionView(
            id = opinion.id.toHexString(),
            author = UserView.from(opinion.author),
            text = opinion.text,
            scale = opinion.scale,
            likes = opinion.likes,
            liked = opinion.liked,
            numberOfComments = opinion.numberOfComments,
        )
    }

}