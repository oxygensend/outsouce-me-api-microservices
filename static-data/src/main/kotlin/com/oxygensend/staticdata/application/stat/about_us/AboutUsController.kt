package com.oxygensend.staticdata.application.stat.about_us

import com.oxygensend.staticdata.application.PrivilegeChecker
import com.oxygensend.staticdata.application.stat.about_us.dto.AboutUsRequest
import com.oxygensend.staticdata.application.stat.about_us.dto.AboutUsView
import io.swagger.v3.oas.annotations.tags.Tag
import org.bson.types.ObjectId
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "About Us")
@RestController
@RequestMapping("/api/v1/static-data/about-us")
internal class AboutUsController(
    private val aboutUsService: AboutUsService,
    private val privilegeChecker: PrivilegeChecker
) {

    @PostMapping( consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createAboutUs(@RequestPart request: AboutUsRequest, @RequestPart image: MultipartFile): AboutUsView {
        privilegeChecker.checkEditorPrivileges()
        return aboutUsService.createAboutUs(request, image)
    }

    @Cacheable("about-us")
    @GetMapping
    fun findAllEnabled(): List<AboutUsView> {
        return aboutUsService.findAllEnabled()
    }

    @GetMapping("/all")
    fun findAll(): List<AboutUsView> {
        privilegeChecker.checkEditorPrivileges()
        return aboutUsService.findAll()
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAboutUs(@PathVariable id: ObjectId) {
        privilegeChecker.checkEditorPrivileges()
        aboutUsService.deleteAboutUs(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateAboutUs(
        @PathVariable id: ObjectId,
        @RequestPart request: AboutUsRequest,
        @RequestPart(required = false) image: MultipartFile?
    ): AboutUsView {
        privilegeChecker.checkEditorPrivileges()
        return aboutUsService.updateAboutUs(id, request, image)
    }

    @GetMapping("/image/{imageName}")
    @ResponseStatus(HttpStatus.OK)
    fun getImage(@PathVariable imageName: String): Resource? {
        return aboutUsService.getImage(imageName)
    }

    @PostMapping("/{id}/enabled")
    @ResponseStatus
    fun enableAboutUs(@PathVariable id: ObjectId) {
        privilegeChecker.checkEditorPrivileges()
        aboutUsService.enable(id)
    }

    @PostMapping("/{id}/disabled")
    @ResponseStatus
    fun disableAboutUs(@PathVariable id: ObjectId) {
        privilegeChecker.checkEditorPrivileges()
        aboutUsService.disable(id)
    }
}