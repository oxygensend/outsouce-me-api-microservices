package com.oxygensend.opinions.infrastructure.mongo.repository

import com.oxygensend.opinions.domain.User
import org.springframework.data.mongodb.repository.MongoRepository

internal interface UserMongoRepository : MongoRepository<User, String> {
}