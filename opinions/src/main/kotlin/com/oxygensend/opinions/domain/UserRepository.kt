package com.oxygensend.opinions.domain

interface UserRepository {

    fun findById(id: String): User?

    fun save(user: User): User

    fun findAll(): List<User>
}