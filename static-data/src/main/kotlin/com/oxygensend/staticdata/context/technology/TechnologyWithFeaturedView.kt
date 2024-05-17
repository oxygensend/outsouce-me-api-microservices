package com.oxygensend.staticdata.context.technology

import com.oxygensend.staticdata.domain.Technology

class TechnologyWithFeaturedView(val name: String, val featured: Boolean) {
    companion object {
        fun from(technology: Technology) = TechnologyWithFeaturedView(technology.name, technology.featured)
    }
}