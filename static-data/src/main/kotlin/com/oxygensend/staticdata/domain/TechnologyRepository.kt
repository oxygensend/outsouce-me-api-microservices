package com.oxygensend.staticdata.domain

interface TechnologyRepository {
    fun findAll(): List<Technology>
    fun findByName(name: String): Technology?
    fun save(technology: Technology): Technology
}