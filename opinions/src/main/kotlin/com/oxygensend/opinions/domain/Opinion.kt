package com.oxygensend.opinions.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
@CompoundIndexes(
    CompoundIndex(name = "receiver.name_idx", def = "{'receiver.name': 1}"),
    CompoundIndex(name = "receiver.name_id_asc_idx", def = "{'receiver.name': 1, 'id': 1}"),
    CompoundIndex(name = "receiver.name_id_desc_idx", def = "{'receiver.name': 1, 'id': -1}"),
    CompoundIndex(name = "receiver.name_author.name_idx", def = "{'receiver.name': 1, 'author.name': 1}")
)
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
