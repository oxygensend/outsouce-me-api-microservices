package com.oxygensend.staticdata.infrastructure.mongo

import com.oxygensend.staticdata.domain.Technology
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

internal interface TechnologyMongoRepository : MongoRepository<Technology, UUID> {
    fun findByName(name: String): Technology?

}