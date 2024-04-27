package com.oxygensend.opinions.domain.aggregate

import com.oxygensend.opinions.domain.User
import com.oxygensend.opinions.domain.aggregate.filter.CommentsFilter
import com.oxygensend.opinions.domain.aggregate.filter.OpinionsFilter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface OpinionAggregateRepository {

    fun findAggregatedOpinions(filter: OpinionsFilter, pageable: Pageable): Page<AggregatedOpinionDto>

    fun getUserOpinionsDetails(user: User): UserOpinionsDetailsDto

    fun getOpinionComments(filter: CommentsFilter, pageable: Pageable): List<CommentDto>
}