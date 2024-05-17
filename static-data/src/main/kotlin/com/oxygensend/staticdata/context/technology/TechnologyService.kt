package com.oxygensend.staticdata.context.technology

import com.oxygensend.staticdata.domain.TechnologyRepository
import org.springframework.stereotype.Service

@Service
class TechnologyService(private val technologyRepository: TechnologyRepository) {

    fun getAllTechnologies(): List<TechnologyView> = technologyRepository.findAll()
        .map { technology -> TechnologyView.from(technology) }

    fun getAllTechnologiesWithDetails(): List<TechnologyWithFeaturedView> = technologyRepository.findAll()
        .map { technology -> TechnologyWithFeaturedView.from(technology) }

}