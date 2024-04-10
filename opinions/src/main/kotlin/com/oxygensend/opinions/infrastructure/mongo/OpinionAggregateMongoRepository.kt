package com.oxygensend.opinions.infrastructure.mongo

import com.oxygensend.opinions.context.OpinionAggregateRepository
import com.oxygensend.opinions.context.dto.*
import com.oxygensend.opinions.context.query.GetCommentsQuery
import com.oxygensend.opinions.context.query.GetOpinionsQuery
import com.oxygensend.opinions.domain.Opinion
import com.oxygensend.opinions.domain.User
import org.springframework.data.domain.Page
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
    override fun findAggregatedOpinions(query: GetOpinionsQuery): Page<AggregatedOpinionDto> {
        getOpinions(query).let {
            return PageableExecutionUtils.getPage(it, query.pageable) {
                countOpinions(query)
            }
        }
    }

    private fun getOpinions(query: GetOpinionsQuery): List<AggregatedOpinionDto> {
        val sort = getOpinionsSortMethod(query)
        val aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where(Opinion::receiver.name).`is`(query.receiver)),
            Aggregation.addFields()
                .addFieldWithValue("likesCount", ArrayOperators.Size.lengthOfArray("\$likes"))
                .addFieldWithValue("numberOfComments", ArrayOperators.Size.lengthOfArray("\$comments"))
                .addFieldWithValue("liked", ArrayOperators.In.arrayOf("\$likes").containsValue("\$author"))
                .build(),
            Aggregation.sort(sort),
            Aggregation.skip(query.pageable.offset),
            Aggregation.limit(query.pageable.pageSize.toLong()),
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

    override fun getUserOpinionsDetails(user: User): UserOpinionsDetailsDto {
        val aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where(Opinion::receiver.name).`is`(user.id)),
            Aggregation.group()
                .count().`as`(UserOpinionsDetailsDto::opinionsCount.name)
                .avg(Opinion::scale.name).`as`(UserOpinionsDetailsDto::opinionsRate.name)
        )
        val results = mongoTemplate.aggregate(aggregation, Opinion::class.java, UserOpinionsDetailsDto::class.java).uniqueMappedResult
        return results ?: UserOpinionsDetailsDto(0, 0.0)
    }

    override fun getOpinionComments(query: GetCommentsQuery): List<CommentDto> {
        val sortDirection = getCommentsSortDirection(query.sort)
        val aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where(Opinion::id.name).`is`(query.opinionId)),
            Aggregation.unwind(Opinion::comments.name, true),
            Aggregation.lookup("user", "comments.author", "_id", "authorDetails"),
            Aggregation.unwind("authorDetails", true),
            Aggregation.sort(Sort.by(sortDirection, "comments._id")),
            Aggregation.skip(query.pageable.offset),
            Aggregation.limit(query.pageable.pageSize.toLong()),
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

    private fun countOpinions(query: GetOpinionsQuery): Long {
        val mongoQuery = Query()
            .addCriteria(Criteria.where(Opinion::receiver.name).`is`(query.receiver))
        return mongoTemplate.count(mongoQuery, Opinion::class.java)
    }

    private fun getOpinionsSortMethod(query: GetOpinionsQuery): Sort = when (query.sortField) {
        OpinionSortField.POPULARITY -> Sort.by(query.direction, "likesCount")
        OpinionSortField.CREATED_AT -> Sort.by(query.direction, Opinion::id.name)
        OpinionSortField.UPDATED_AT -> Sort.by(query.direction, Opinion::updatedAt.name)
    }
}