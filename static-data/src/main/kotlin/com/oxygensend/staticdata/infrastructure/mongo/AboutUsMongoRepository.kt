package com.oxygensend.staticdata.infrastructure.mongo

import com.oxygensend.staticdata.domain.AboutUs
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

internal interface AboutUsMongoRepository : MongoRepository<AboutUs, UUID> {
}