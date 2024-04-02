package com.oxygensend.staticdata.infrastructure.mongo

import com.oxygensend.staticdata.domain.Technology
import org.springframework.data.mongodb.repository.MongoRepository

internal interface TechnologyMongoRepository : MongoRepository<Technology, String> {
    fun findByName(name: String): Technology?
    fun existsByName(name: String): Boolean

}