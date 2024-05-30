package com.oxygensend.staticdata.domain

import org.bson.types.ObjectId

interface AboutUsRepository {

    fun save(aboutUs: AboutUs): AboutUs
    fun findEnabled(): List<AboutUs>
    fun delete(aboutUs: AboutUs)
    fun findById(id: ObjectId): AboutUs?

}