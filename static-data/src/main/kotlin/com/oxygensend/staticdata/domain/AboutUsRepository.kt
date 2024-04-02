package com.oxygensend.staticdata.domain

interface AboutUsRepository {

    fun save(aboutUs: AboutUs): AboutUs
    fun findEnabled(): AboutUs?

}