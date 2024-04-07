package com.oxygensend.opinions.context

import com.oxygensend.commons_jdk.PagedListView
import com.oxygensend.opinions.context.dto.CommentSort
import com.oxygensend.opinions.context.dto.OpinionSortField
import com.oxygensend.opinions.context.query.GetCommentsQuery
import com.oxygensend.opinions.context.query.GetOpinionsQuery
import com.oxygensend.opinions.context.request.AddCommentRequest
import com.oxygensend.opinions.context.request.CreateOpinionRequest
import com.oxygensend.opinions.context.request.LikeRequest
import com.oxygensend.opinions.context.request.UpdateOpinionRequest
import com.oxygensend.opinions.context.view.CommentView
import com.oxygensend.opinions.context.view.OpinionView
import org.bson.types.ObjectId
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/opinions")
class OpinionController(private val opinionService: OpinionService) {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    fun getOpinions(
        pageable: Pageable,
        @RequestParam(required = true) receiver: String,
        @RequestParam(defaultValue = "CREATED_AT") sort: OpinionSortField,
        @RequestParam(defaultValue = "DESC") direction: Sort.Direction
    ): PagedListView<OpinionView> {
        val query = GetOpinionsQuery(
            pageable = pageable,
            receiver = receiver,
            sortField = sort,
            direction = direction
        );

        return opinionService.getOpinions(query);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{opinionId}")
    fun updateOpinion(
        @PathVariable opinionId: ObjectId,
        @Validated @RequestBody request: UpdateOpinionRequest
    ) {
        opinionService.updateOpinion(opinionId, request.toCommand());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{opinionId}")
    fun deleteOpinion(
        @PathVariable opinionId: ObjectId
    ) {
        opinionService.deleteOpinion(opinionId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createOpinion(
        @Validated @RequestBody request: CreateOpinionRequest
    ): OpinionView {
        return opinionService.createOpinion(request.toCommand());
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{opinionId}/like")
    fun addLike(
        @PathVariable opinionId: ObjectId,
        @Validated @RequestBody request: LikeRequest
    ) {
        opinionService.addLike(opinionId, request.userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{opinionId}/dislike")
    fun addDislike(
        @PathVariable opinionId: ObjectId,
        @Validated @RequestBody request: LikeRequest
    ) {
        opinionService.addDislike(opinionId, request.userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{opinionId}/comments")
    fun addComment(
        @PathVariable opinionId: ObjectId,
        @Validated @RequestBody request: AddCommentRequest
    ): CommentView {
        return opinionService.addComment(opinionId, request.toCommand());
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{opinionId}/comments/{commentId}")
    fun deleteComment(
        @PathVariable opinionId: ObjectId,
        @PathVariable commentId: ObjectId
    ) {
        opinionService.deleteComment(opinionId, commentId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{opinionId}/comments")
    fun getComments(
        @PathVariable opinionId: ObjectId,
        @RequestParam(defaultValue = "NEWEST") sort: CommentSort,
        pageable: Pageable
    ): PagedListView<CommentView> {
        return opinionService.getComments(
            GetCommentsQuery(
                opinionId = opinionId,
                pageable = pageable,
                sort = sort
            )
        );
    }
}