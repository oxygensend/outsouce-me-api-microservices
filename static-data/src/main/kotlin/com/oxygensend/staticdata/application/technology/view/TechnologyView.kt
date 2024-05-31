package com.oxygensend.staticdata.application.technology.view

import com.oxygensend.staticdata.domain.Technology

class TechnologyView(name: String, featured: Boolean, val id: String) : BaseTechnologyView(name) {
    companion object {
        fun from(technology: Technology) = TechnologyView(technology.name, technology.featured, technology.id)
    }
}