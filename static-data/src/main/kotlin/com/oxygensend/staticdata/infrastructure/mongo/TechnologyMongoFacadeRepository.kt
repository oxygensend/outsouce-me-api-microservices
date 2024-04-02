package com.oxygensend.staticdata.infrastructure.mongo

import com.oxygensend.staticdata.domain.Technology
import com.oxygensend.staticdata.domain.TechnologyRepository
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.stereotype.Repository

@Repository
internal class TechnologyMongoFacadeRepository(private val technologyMongoRepository: TechnologyMongoRepository) : TechnologyRepository {
    override fun findAll(): List<Technology> {
        val sort = Sort.by(Sort.Order(Direction.DESC, "featured"))
        return technologyMongoRepository.findAll(sort)
    }

    override fun findByName(name: String): Technology? = technologyMongoRepository.findByName(name)

    override fun save(technology: Technology): Technology = technologyMongoRepository.save(technology)
    override fun saveAll(technologies: List<Technology>): List<Technology> = technologyMongoRepository.saveAll(technologies)
    override fun existsByName(name: String): Boolean = technologyMongoRepository.existsByName(name);
}