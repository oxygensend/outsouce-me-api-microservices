package com.oxygensend.staticdata.domain

interface TechnologyRepository {
    fun findAll(): List<Technology>
    fun findByName(name: String): Technology?
    fun save(technology: Technology): Technology
    fun saveAll(technologies: List<Technology>): List<Technology>
    fun existsByName(name: String): Boolean
}