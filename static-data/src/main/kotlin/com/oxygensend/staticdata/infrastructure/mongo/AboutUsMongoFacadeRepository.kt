package com.oxygensend.staticdata.infrastructure.mongo

import com.oxygensend.staticdata.domain.AboutUs
import com.oxygensend.staticdata.domain.AboutUsRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Repository

@Repository
internal class AboutUsMongoFacadeRepository(private val aboutUsMongoRepository: AboutUsMongoRepository) : AboutUsRepository {

    override fun save(aboutUs: AboutUs): AboutUs = aboutUsMongoRepository.save(aboutUs)
    override fun findEnabled(): List<AboutUs> = aboutUsMongoRepository.findByEnabledTrue()
    override fun delete(aboutUs: AboutUs) = aboutUsMongoRepository.delete(aboutUs)
    override fun findById(id: ObjectId): AboutUs? = aboutUsMongoRepository.findById(id).orElse(null)
    override fun findAll(): List<AboutUs> = aboutUsMongoRepository.findAll()

}