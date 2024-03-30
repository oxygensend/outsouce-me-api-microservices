package com.oxygensend.staticdata.infrastructure.mongo

import com.oxygensend.staticdata.domain.Address
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

internal interface AddressMongoRepository : MongoRepository<Address, UUID> {
}