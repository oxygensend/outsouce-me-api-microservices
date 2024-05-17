package com.oxygensend.staticdata.context.technology

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "Technology")
@RestController
@CrossOrigin
@RequestMapping("/api/v1/static-data")
internal class TechnologyController(private val technologyService: TechnologyService) {

    @GetMapping("/technologies")
    fun getAllTechnologies(): List<TechnologyView> = technologyService.getAllTechnologies();

    @GetMapping("/technologies/details")
    fun getAllTechnologiesWithDetails(): List<TechnologyWithFeaturedView> = technologyService.getAllTechnologiesWithDetails();

}