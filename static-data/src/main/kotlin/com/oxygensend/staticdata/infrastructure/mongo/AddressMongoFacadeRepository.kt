package com.oxygensend.staticdata.infrastructure.mongo

import com.oxygensend.staticdata.domain.Address
import com.oxygensend.staticdata.domain.AddressRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
internal class AddressMongoFacadeRepository(
    private val addressMongoRepository: AddressMongoRepository,
    private val mongoTemplate: MongoTemplate
) : AddressRepository {
    @Cacheable("addresses")
    override fun findAll(search: String?): List<Address> {
        return mongoTemplate.find(Query(), Address::class.java)
    }

    override fun save(address: Address): Address = addressMongoRepository.save(address)
    override fun findByCity(city: String): Address? = addressMongoRepository.findByCity(city)
    override fun saveBatch(addresses: List<Address>) {
        addressMongoRepository.saveAll(addresses)
    }

    override fun count(): Long = addressMongoRepository.count()

    override fun saveAll(addresses: List<Address>) {
        addressMongoRepository.saveAll(addresses)
    }
}