package com.oxygensend.opinions.context.service

import com.oxygensend.commons_jdk.PagedListView
import com.oxygensend.opinions.context.command.AddCommentCommand
import com.oxygensend.opinions.context.command.CreateOpinionCommand
import com.oxygensend.opinions.context.command.UpdateOpinionCommand
import com.oxygensend.opinions.context.event.OpinionDetailsRecalculateEvent
import com.oxygensend.opinions.context.utils.updateIfDefined
import com.oxygensend.opinions.context.view.CommentView
import com.oxygensend.opinions.context.view.OpinionView
import com.oxygensend.opinions.domain.Opinion
import com.oxygensend.opinions.domain.OpinionRepository
import com.oxygensend.opinions.domain.UserRepository
import com.oxygensend.opinions.domain.aggregate.OpinionAggregateRepository
import com.oxygensend.opinions.domain.aggregate.filter.CommentsFilter
import com.oxygensend.opinions.domain.aggregate.filter.OpinionsFilter
import com.oxygensend.opinions.domain.exception.*
import org.bson.types.ObjectId
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class OpinionService(
    private val opinionRepository: OpinionRepository,
    private val opinionAggregateRepository: OpinionAggregateRepository,
    private val userRepository: UserRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun getOpinions(filter: OpinionsFilter, pageable: Pageable): PagedListView<OpinionView> {
        val paginator = opinionAggregateRepository.findAggregatedOpinions(filter, pageable)
        val data = paginator.map { OpinionView.from(it) }.toList()
        return PagedListView(data, paginator.numberOfElements, paginator.number, paginator.totalPages)
    }

    fun updateOpinion(opinionId: ObjectId, command: UpdateOpinionCommand) {
        val opinion = opinionRepository.findById(opinionId) ?: throw OpinionNotFoundException()

        updateIfDefined(command.text) {
            opinion.text = command.text.value!!
        }
        updateIfDefined(command.scale) {
            opinion.scale = command.scale.value!!
            publishRecalculationEvent(opinion.receiver)
        }

        opinionRepository.save(opinion)
    }

    fun deleteOpinion(opinionId: ObjectId) {
        val opinion = opinionRepository.findById(opinionId) ?: throw OpinionNotFoundException()
        opinionRepository.delete(opinion)
        publishRecalculationEvent(opinion.receiver)
    }

    fun createOpinion(command: CreateOpinionCommand): OpinionView {
        validateIfCreationIsPossible(command.authorId, command.receiverId)
        val author = userRepository.findById(command.authorId) ?: throw NoSuchUserException("Author with id ${command.authorId} not found")
        val opinion = Opinion(
            id = ObjectId(),
            author = command.authorId,
            receiver = command.receiverId,
            text = command.text,
            scale = command.scale
        )

        opinionRepository.save(opinion)
        publishRecalculationEvent(opinion.receiver)
        return OpinionView.from(opinion, author)
    }


    fun addLike(opinionId: ObjectId, userId: String) {
        val opinion = opinionRepository.findById(opinionId) ?: throw OpinionNotFoundException()
        userRepository.findById(userId) ?: throw NoSuchUserException("User with id $userId not found")
        if (opinion.hasLiked(userId)) {
            throw OpinionLikeException.opinionAlreadyLiked()
        }
        opinion.addLike(userId)
        opinionRepository.save(opinion)
    }

    fun addDislike(opinionId: ObjectId, userId: String) {
        val opinion = opinionRepository.findById(opinionId) ?: throw OpinionNotFoundException()
        userRepository.findById(userId) ?: throw NoSuchUserException("User with id $userId not found")
        if (!opinion.hasLiked(userId)) {
            throw OpinionLikeException.opinionNotLiked()
        }
        opinion.removeLike(userId)
        opinionRepository.save(opinion)
    }

    fun addComment(opinionId: ObjectId, command: AddCommentCommand): CommentView {
        val opinion = opinionRepository.findById(opinionId) ?: throw OpinionNotFoundException()
        val author = userRepository.findById(command.author) ?: throw NoSuchUserException("User with id ${command.author} not found")

        val comment = Opinion.Comment(ObjectId(), command.author, command.text)
        opinion.addComment(comment)
        opinionRepository.save(opinion)

        return CommentView.from(comment, author)
    }

    fun deleteComment(opinionId: ObjectId, commentId: ObjectId) {
        val opinion = opinionRepository.findById(opinionId) ?: throw OpinionNotFoundException()
        val comment = opinion.findCommentById(commentId) ?: throw NoSuchCommentException("Comment with id $commentId not found")
        opinion.removeComment(comment)
        opinionRepository.save(opinion)
    }

    fun getComments(filter: CommentsFilter, pageable: Pageable): PagedListView<CommentView> {
        val opinion = opinionRepository.findById(filter.opinionId) ?: throw OpinionNotFoundException()
        val comments = opinionAggregateRepository.getOpinionComments(filter, pageable)
            .map { CommentView.from(it) }

        return PagedListView(comments, comments.size, pageable.pageNumber, opinion.comments.size)
    }

    private fun validateIfCreationIsPossible(authorId: String, receiverId: String) {
        userRepository.findById(receiverId) ?: throw NoSuchUserException("Receiver with id $receiverId not found")

        opinionRepository.existsByAuthorAndReceiver(authorId, receiverId).takeIf { it }?.let {
            throw OpinionAlreadyExistException("User $receiverId already has opinion from $authorId")
        }
    }

    private fun publishRecalculationEvent(receiver: String) {
        return applicationEventPublisher.publishEvent(OpinionDetailsRecalculateEvent(receiver))
    }

}