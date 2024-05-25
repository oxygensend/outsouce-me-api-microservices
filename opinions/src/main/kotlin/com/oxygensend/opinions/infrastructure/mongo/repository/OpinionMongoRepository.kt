package com.oxygensend.opinions.infrastructure.mongo.repository

import com.oxygensend.opinions.domain.Opinion
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.CrudRepository

internal interface OpinionMongoRepository : MongoRepository<Opinion, ObjectId> {

    fun existsByAuthorAndReceiver(author: String, receiver: String): Boolean
}
