package com.oxygensend.staticdata.infrastructure.mongo

import com.oxygensend.staticdata.domain.Address
import com.oxygensend.staticdata.domain.AddressRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
internal class AddressMongoFacadeRepository(
    private val addressMongoRepository: AddressMongoRepository,
    private val mongoTemplate: MongoTemplate
) : AddressRepository {
    override fun findAll(search: String?): List<Address> {
        val query = Query()
            .apply {
                search?.let {
                    addCriteria(Criteria.where(Address::postCodes.name).regex(search, "i"))
                }
            };

        return mongoTemplate.find(query, Address::class.java)
    }

    override fun save(address: Address): Address = addressMongoRepository.save(address)
}