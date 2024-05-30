package com.oxygensend.staticdata.context.stat.about_us

import com.oxygensend.staticdata.domain.AboutUsRepository
import org.springframework.web.multipart.MultipartFile

class AboutUsService(private val aboutUsRepository: AboutUsRepository) {

    fun createAboutUs(request: CreateAboutUsRequest, image: MultipartFile): AboutUsView {
        val imageUrl = imageService.uploadImage(image)
        val aboutUs = AboutUs(
            title = request.title!!,
            description = request.description!!,
            enabled = request.enabled,
            imageUrl = imageUrl
        )
        val savedAboutUs = aboutUsRepository.save(aboutUs)
        return AboutUsView(
            id = savedAboutUs.id!!,
            title = savedAboutUs.title,
            description = savedAboutUs.description,
            enabled = savedAboutUs.enabled,
            imageUrl = savedAboutUs.imageUrl
        )
    }
}