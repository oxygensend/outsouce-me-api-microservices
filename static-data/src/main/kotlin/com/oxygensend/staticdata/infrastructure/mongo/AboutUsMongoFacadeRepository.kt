package com.oxygensend.staticdata.infrastructure.mongo

import com.oxygensend.staticdata.domain.AboutUs
import com.oxygensend.staticdata.domain.AboutUsRepository
import org.springframework.stereotype.Repository

@Repository
internal class AboutUsMongoFacadeRepository(private val aboutUsMongoRepository: AboutUsMongoRepository) : AboutUsRepository {

    override fun save(aboutUs: AboutUs): AboutUs = aboutUsMongoRepository.save(aboutUs)

    override fun findEnabled(): AboutUs? {
        TODO("Not yet implemented")
    }
}