package com.oxygensend.staticdata.context.technology.view

import com.oxygensend.staticdata.domain.Technology

class TechnologyView(name: String, featured: Boolean, val id: String) : BaseTechnologyView(name) {
    companion object {
        fun from(technology: Technology) = TechnologyView(technology.name, technology.featured, technology.id)
    }
}