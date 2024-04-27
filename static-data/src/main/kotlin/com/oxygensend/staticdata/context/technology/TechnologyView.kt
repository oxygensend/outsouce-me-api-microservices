package com.oxygensend.staticdata.context.technology

import com.oxygensend.staticdata.domain.Technology

data class TechnologyView(val name: String){
    companion object {
        fun from(technology: Technology) = TechnologyView(technology.name)
    }
}
