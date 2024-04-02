package com.oxygensend.staticdata.context.service

import com.oxygensend.staticdata.context.dto.TechnologyView
import com.oxygensend.staticdata.domain.TechnologyRepository
import org.springframework.stereotype.Service

@Service
class TechnologyService(private val technologyRepository: TechnologyRepository) {

    fun getAllTechnologies(): List<TechnologyView> = technologyRepository.findAll()
        .map { technology -> TechnologyView.from(technology) }

}