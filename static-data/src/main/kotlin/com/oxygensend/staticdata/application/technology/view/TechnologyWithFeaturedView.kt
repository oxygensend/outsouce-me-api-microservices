package com.oxygensend.staticdata.application.technology.view

import com.oxygensend.staticdata.domain.Technology

class TechnologyWithFeaturedView(val id: String, name: String, val featured: Boolean) : BaseTechnologyView(name) {
    companion object {
        fun from(technology: Technology) = TechnologyWithFeaturedView(technology.id, technology.name, technology.featured)
    }
}