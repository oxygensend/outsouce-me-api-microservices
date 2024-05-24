package com.oxygensend.staticdata.context.technology

import com.oxygensend.staticdata.context.technology.view.BaseTechnologyView
import com.oxygensend.staticdata.context.technology.view.TechnologyView
import com.oxygensend.staticdata.context.technology.view.TechnologyWithFeaturedView
import com.oxygensend.staticdata.domain.Technology
import com.oxygensend.staticdata.domain.TechnologyRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class TechnologyService(private val technologyRepository: TechnologyRepository) {

    fun getAllTechnologies(): List<BaseTechnologyView> = technologyRepository.findAll()
        .map { technology -> BaseTechnologyView.from(technology) }

    fun getAllTechnologiesWithDetails(): List<TechnologyWithFeaturedView> = technologyRepository.findAll()
        .map { technology -> TechnologyWithFeaturedView.from(technology) }

    fun updateTechnology(id: String, featured: Boolean): TechnologyView {
        val technology = technologyRepository.findById(id) ?: throw IllegalArgumentException("Technology not found")
        technology.featured = featured
        technologyRepository.save(technology)
        return TechnologyView.from(technology)
    }

    fun deleteTechnology(id: String): TechnologyView {
        val technology = technologyRepository.findById(id) ?: throw IllegalArgumentException("Technology not found")
        technologyRepository.delete(technology)
        return TechnologyView.from(technology)
    }

    fun createTechnology(name: String, featured: Boolean): TechnologyView {
        val technology = technologyRepository.save(Technology(id = ObjectId().toHexString(), name = name, featured = featured))
        return TechnologyView.from(technology)
    }
}