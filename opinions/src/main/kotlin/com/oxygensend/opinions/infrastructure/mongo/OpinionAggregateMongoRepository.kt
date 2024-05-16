package com.oxygensend.opinions.infrastructure.mongo

import com.oxygensend.opinions.domain.Opinion
import com.oxygensend.opinions.domain.User
import com.oxygensend.opinions.domain.aggregate.AggregatedOpinionDto
import com.oxygensend.opinions.domain.aggregate.CommentDto
import com.oxygensend.opinions.domain.aggregate.OpinionAggregateRepository
import com.oxygensend.opinions.domain.aggregate.UserOpinionsDetailsDto
import com.oxygensend.opinions.domain.aggregate.filter.CommentSort
import com.oxygensend.opinions.domain.aggregate.filter.CommentsFilter
import com.oxygensend.opinions.domain.aggregate.filter.OpinionSortField
import com.oxygensend.opinions.domain.aggregate.filter.OpinionsFilter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.ArrayOperators
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Component

@Component
internal class OpinionAggregateMongoRepository(private val mongoTemplate: MongoTemplate) : OpinionAggregateRepository {
    override fun findAggregatedOpinions(filter: OpinionsFilter, pageable: Pageable): Page<AggregatedOpinionDto> {
        getOpinions(filter, pageable).let {
            return PageableExecutionUtils.getPage(it, pageable) {
                countOpinions(filter)
            }
        }
    }

    private fun getOpinions(filter: OpinionsFilter, pageable: Pageable): List<AggregatedOpinionDto> {
        val sort = getOpinionsSortMethod(filter)
        val aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where(Opinion::receiver.name).`is`(filter.receiver)),
            Aggregation.addFields()
                .addFieldWithValue("likesCount", ArrayOperators.Size.lengthOfArray("\$likes"))
                .addFieldWithValue("numberOfComments", ArrayOperators.Size.lengthOfArray("\$comments"))
                .addFieldWithValue("liked", ArrayOperators.In.arrayOf("\$likes").containsValue("\$author"))
                .build(),
            Aggregation.sort(sort),
            Aggregation.skip(pageable.offset),
            Aggregation.limit(pageable.pageSize.toLong()),
            Aggregation.lookup("user", "author", "_id", "authorDetails"),
            Aggregation.unwind("authorDetails"),
            Aggregation.project()
                .and("_id").`as`(AggregatedOpinionDto::id.name)
                .and("authorDetails").`as`(AggregatedOpinionDto::author.name)
                .and("receiver").`as`(AggregatedOpinionDto::receiver.name)
                .and("text").`as`(AggregatedOpinionDto::text.name)
                .and("scale").`as`(AggregatedOpinionDto::scale.name)
                .and("likesCount").`as`(AggregatedOpinionDto::likes.name)
                .and("liked").`as`(AggregatedOpinionDto::liked.name)
                .and("numberOfComments").`as`(AggregatedOpinionDto::numberOfComments.name)

        )

        return mongoTemplate.aggregate(aggregation, Opinion::class.java, AggregatedOpinionDto::class.java).mappedResults
    }

    override fun getUserOpinionsDetails(userId: String): UserOpinionsDetailsDto {
        val aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where(Opinion::receiver.name).`is`(userId)),
            Aggregation.group()
                .count().`as`(UserOpinionsDetailsDto::opinionsCount.name)
                .avg(Opinion::scale.name).`as`(UserOpinionsDetailsDto::opinionsRate.name)
        )
        val results = mongoTemplate.aggregate(aggregation, Opinion::class.java, UserOpinionsDetailsDto::class.java).uniqueMappedResult
        return results ?: UserOpinionsDetailsDto(0, 0.0)
    }

    override fun getOpinionComments(filter: CommentsFilter,pageable: Pageable): List<CommentDto> {
        val sortDirection = getCommentsSortDirection(filter.sort)
        val aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where(Opinion::id.name).`is`(filter.opinionId)),
            Aggregation.unwind(Opinion::comments.name, true),
            Aggregation.lookup("user", "comments.author", "_id", "authorDetails"),
            Aggregation.unwind("authorDetails", true),
            Aggregation.sort(Sort.by(sortDirection, "comments._id")),
            Aggregation.skip(pageable.offset),
            Aggregation.limit(pageable.pageSize.toLong()),
            Aggregation.project()
                .and("comments._id").`as`("_id")
                .and("authorDetails").`as`(CommentDto::author.name)
                .and("comments.text").`as`(CommentDto::text.name)
        )

        return mongoTemplate.aggregate(aggregation, Opinion::class.java, CommentDto::class.java).mappedResults;
    }

    private fun getCommentsSortDirection(sort: CommentSort): Sort.Direction = when (sort) {
        CommentSort.OLDEST -> Sort.Direction.ASC
        CommentSort.NEWEST -> Sort.Direction.DESC
    }

    private fun countOpinions(filter: OpinionsFilter): Long {
        val mongoQuery = Query()
            .addCriteria(Criteria.where(Opinion::receiver.name).`is`(filter.receiver))
        return mongoTemplate.count(mongoQuery, Opinion::class.java)
    }

    private fun getOpinionsSortMethod(filter: OpinionsFilter): Sort = when (filter.sortField) {
        OpinionSortField.POPULARITY -> Sort.by(filter.direction.name, "likesCount")
        OpinionSortField.CREATED_AT -> Sort.by(filter.direction.name, Opinion::id.name)
        OpinionSortField.UPDATED_AT -> Sort.by(filter.direction.name, Opinion::updatedAt.name)
    }
}