package com.oxygensend.staticdata.application.stat.about_us

import com.oxygensend.staticdata.application.stat.about_us.dto.AboutUsRequest
import com.oxygensend.staticdata.application.stat.about_us.dto.AboutUsView
import com.oxygensend.staticdata.application.storage.ImageService
import com.oxygensend.staticdata.domain.AboutUs
import com.oxygensend.staticdata.domain.AboutUsRepository
import com.oxygensend.staticdata.domain.exception.ResourceNotFoundException
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class AboutUsService internal constructor(
    private val aboutUsRepository: AboutUsRepository,
    private val imageService: ImageService,
    private val aboutUsViewFactory: AboutUsViewFactory
) {

    fun createAboutUs(request: AboutUsRequest, image: MultipartFile): AboutUsView {
        val imageName = imageService.create(image)
        val aboutUs = AboutUs(
            id = ObjectId(),
            title = request.title!!,
            description = request.description!!,
            enabled = request.enabled,
            imageName = imageName
        )

        val savedAboutUs = aboutUsRepository.save(aboutUs)
        return aboutUsViewFactory.createView(savedAboutUs);
    }

    fun findAllEnabled(): List<AboutUsView> = aboutUsRepository.findEnabled().map { aboutUsViewFactory.createView(it) }

    fun deleteAboutUs(id: ObjectId) {
        val aboutUs = aboutUsRepository.findById(id) ?: throw ResourceNotFoundException("About Us with id $id not found")
        aboutUsRepository.delete(aboutUs)
        imageService.delete(aboutUs.imageName)
    }

    fun updateAboutUs(id: ObjectId, request: AboutUsRequest, image: MultipartFile?): AboutUsView {
        val aboutUs = aboutUsRepository.findById(id) ?: throw ResourceNotFoundException("About Us with id $id not found")
        val imageName = image?.let { imageService.create(it) } ?: aboutUs.imageName
        val updatedAboutUs = aboutUs.copy(
            title = request.title ?: aboutUs.title,
            description = request.description ?: aboutUs.description,
            enabled = request.enabled,
            imageName = imageName
        )

        val savedAboutUs = aboutUsRepository.save(updatedAboutUs)
        return aboutUsViewFactory.createView(savedAboutUs)
    }

    fun getImage(imageName: String) = imageService.load(imageName)
}