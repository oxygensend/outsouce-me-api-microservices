package com.oxygensend.opinions.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.time.LocalDateTime

class Opinion(
    @Id
    var id: ObjectId,
    var author: String,
    var receiver: String,
    var scale: Int,
    var text: String? = null,
) {
    var likes: List<String> = emptyList()
    var comments: List<Comment> = emptyList()
    var updatedAt: LocalDateTime? = null

    fun addLike(userId: String) {
        likes += userId
    }

    fun hasLiked(userId: String): Boolean = likes.contains(userId)

    fun removeLike(userId: String) {
        likes -= userId
    }

    fun addComment(comment: Comment) {
        comments += comment
    }

    fun removeComment(comment: Comment) {
        comments -= comment
    }

    fun findCommentById(commentId: ObjectId): Comment? {
        return comments.find { it.id == commentId }
    }

    data class Comment(
        val id: ObjectId,
        val author: String,
        var text: String,
    )

}
