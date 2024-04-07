package com.oxygensend.opinions.infrastructure.mongo

import com.oxygensend.opinions.domain.Opinion
import com.oxygensend.opinions.domain.OpinionRepository
import org.bson.types.ObjectId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class OpinionMongoFacadeRepository(
    private val opinionMongoRepository: OpinionMongoRepository,
) : OpinionRepository {

    override fun save(opinion: Opinion): Opinion = opinionMongoRepository.save(opinion)

    override fun findById(id: ObjectId): Opinion? = opinionMongoRepository.findByIdOrNull(id)

    override fun delete(opinion: Opinion) = opinionMongoRepository.delete(opinion)

    override fun existsByAuthorAndReceiver(author: String, receiver: String): Boolean = opinionMongoRepository.existsByAuthorAndReceiver(author, receiver)

}