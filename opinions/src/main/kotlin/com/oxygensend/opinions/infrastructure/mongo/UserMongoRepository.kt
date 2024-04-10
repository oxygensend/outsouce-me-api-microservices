package com.oxygensend.opinions.infrastructure.mongo

import com.oxygensend.opinions.domain.User
import org.springframework.data.mongodb.repository.MongoRepository

internal interface UserMongoRepository : MongoRepository<User, String> {
}