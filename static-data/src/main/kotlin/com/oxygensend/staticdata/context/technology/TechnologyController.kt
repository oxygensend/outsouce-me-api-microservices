package com.oxygensend.staticdata.context.technology

import com.oxygensend.staticdata.context.PrivilegeChecker
import com.oxygensend.staticdata.context.technology.view.BaseTechnologyView
import com.oxygensend.staticdata.context.technology.view.TechnologyView
import com.oxygensend.staticdata.context.technology.view.TechnologyWithFeaturedView
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*


@Tag(name = "Technology")
@RestController
@CrossOrigin
@RequestMapping("/api/v1/static-data")
internal class TechnologyController(
    private val technologyService: TechnologyService,
    private val privilegeChecker: PrivilegeChecker
) {


    @Cacheable("technologies")
    @GetMapping("/technologies")
    fun getAllTechnologies(): List<BaseTechnologyView> = technologyService.getAllTechnologies();

    @GetMapping("/technologies/details")
    fun getAllTechnologiesWithDetails(): List<TechnologyWithFeaturedView> = technologyService.getAllTechnologiesWithDetails();

    @PatchMapping("/technologies/{id}")
    fun updateTechnology(@PathVariable id: String, @RequestBody featured: Boolean): TechnologyView {
        privilegeChecker.checkEditorPrivileges()
        return technologyService.updateTechnology(id, featured)
    }

    @DeleteMapping("/technologies/{id}")
    fun deleteTechnology(@PathVariable id: String): TechnologyView {
        privilegeChecker.checkEditorPrivileges()
        return technologyService.deleteTechnology(id)
    }

    @PostMapping("/technologies")
    fun createTechnology(@RequestBody request: TechnologyRequest): TechnologyView {
        privilegeChecker.checkEditorPrivileges()
        return technologyService.createTechnology(request.name!!, request.featured!!)
    }
}