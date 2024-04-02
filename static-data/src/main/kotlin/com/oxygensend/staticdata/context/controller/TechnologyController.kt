package com.oxygensend.staticdata.context.controller

import com.oxygensend.staticdata.context.dto.TechnologyView
import com.oxygensend.staticdata.context.service.TechnologyService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "Technology")
@RestController
@RequestMapping("/api/v1/static-data")
class TechnologyController(private val technologyService: TechnologyService) {

    @GetMapping("/technologies")
    fun getAllTechnologies(): List<TechnologyView> = technologyService.getAllTechnologies();

}