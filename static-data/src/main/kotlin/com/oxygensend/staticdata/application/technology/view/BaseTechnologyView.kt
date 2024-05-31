package com.oxygensend.staticdata.application.technology.view

import com.oxygensend.staticdata.domain.Technology

open class BaseTechnologyView(val name: String){
    companion object {
        fun from(technology: Technology) = BaseTechnologyView(technology.name)
    }
}
