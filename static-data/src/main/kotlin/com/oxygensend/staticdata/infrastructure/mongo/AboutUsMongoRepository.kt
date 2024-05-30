package com.oxygensend.staticdata.infrastructure.mongo

import com.oxygensend.staticdata.domain.AboutUs
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

internal interface AboutUsMongoRepository : MongoRepository<AboutUs, ObjectId> {

    fun findByEnabledTrue(): List<AboutUs>
}