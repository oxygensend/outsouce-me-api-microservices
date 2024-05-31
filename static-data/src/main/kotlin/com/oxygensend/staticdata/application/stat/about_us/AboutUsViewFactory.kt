package com.oxygensend.staticdata.application.stat.about_us

import com.oxygensend.staticdata.application.stat.about_us.dto.AboutUsView
import com.oxygensend.staticdata.domain.AboutUs
import com.oxygensend.staticdata.infrastructure.StaticDataProperties
import org.springframework.stereotype.Component

@Component
internal class AboutUsViewFactory(private val staticDataProperties: StaticDataProperties) {
    fun createView(aboutUs: AboutUs): AboutUsView {
        return AboutUsView(
            id = aboutUs.id.toHexString(),
            title = aboutUs.title,
            description = aboutUs.description,
            enabled = aboutUs.enabled,
            imageUrl = staticDataProperties.aboutUsImageServerUrl + "/" + aboutUs.imageName
        )
    }
}