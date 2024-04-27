package com.oxygensend.opinions.context.view

import com.oxygensend.opinions.domain.aggregate.CommentDto
import com.oxygensend.opinions.domain.Opinion
import com.oxygensend.opinions.domain.User
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class CommentView(
    val id: String,
    val author: UserView,
    val text: String,
    val createdAt: LocalDateTime
) {


    companion object {
        fun from(commentDto: CommentDto) = CommentView(
            id = commentDto.id.toHexString(),
            author = UserView.from(commentDto.author),
            text = commentDto.text,
            createdAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(commentDto.id.timestamp.toLong()), ZoneId.systemDefault())
        )

        fun from(comment: Opinion.Comment, author: User) = CommentView(
            id = comment.id.toHexString(),
            author = UserView.from(author),
            text = comment.text,
            createdAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(comment.id.timestamp.toLong()), ZoneId.systemDefault())
        )

    }
}

