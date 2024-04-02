package com.oxygensend.staticdata.infrastructure.mongo

import com.oxygensend.staticdata.domain.Address
import org.springframework.data.mongodb.repository.MongoRepository

internal interface AddressMongoRepository : MongoRepository<Address, String> {
    fun findByCity(city: String): Address?

}