package com.oxygensend.staticdata.application.technology.view

import com.oxygensend.staticdata.domain.Technology

class TechnologyWithFeaturedView(name: String, val featured: Boolean) : BaseTechnologyView(name) {
    companion object {
        fun from(technology: Technology) = TechnologyWithFeaturedView(technology.name, technology.featured)
    }
}