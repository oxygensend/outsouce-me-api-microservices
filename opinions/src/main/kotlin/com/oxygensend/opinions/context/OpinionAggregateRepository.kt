package com.oxygensend.opinions.context

import com.oxygensend.opinions.context.dto.AggregatedOpinionDto
import com.oxygensend.opinions.context.dto.CommentDto
import com.oxygensend.opinions.context.dto.UserOpinionsDetailsDto
import com.oxygensend.opinions.context.query.GetCommentsQuery
import com.oxygensend.opinions.context.query.GetOpinionsQuery
import com.oxygensend.opinions.domain.User
import org.springframework.data.domain.Page

interface OpinionAggregateRepository {

    fun findAggregatedOpinions(query: GetOpinionsQuery): Page<AggregatedOpinionDto>

    fun getUserOpinionsDetails(user: User): UserOpinionsDetailsDto

    fun getOpinionComments(query: GetCommentsQuery): List<CommentDto>
}