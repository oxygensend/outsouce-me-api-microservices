package com.oxygensend.staticdata.context.stat.about_us

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "About Us")
@RestController
@RequestMapping("/api/v1/static-data/about-us")
internal class AboutUsController(private val  aboutUsService: AboutUsService) {


    @PostMapping
    fun createAboutUs(@RequestPart request: CreateAboutUsRequest, @RequestPart image: MultipartFile): AboutUsView {
        return aboutUsService.createAboutUs(request, image)
    }
}