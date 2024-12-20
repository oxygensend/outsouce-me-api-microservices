package com.oxygensend.opinions.infrastructure.mongo.repository

import com.oxygensend.opinions.domain.User
import com.oxygensend.opinions.domain.UserRepository
import org.springframework.stereotype.Component

@Component
internal class UserMongoFacadeRepository(
    private val userMongoRepository: UserMongoRepository
) : UserRepository {

    override fun findById(id: String): User? = userMongoRepository.findById(id).orElse(null)
    override fun save(user: User): User = userMongoRepository.save(user)
    override fun findAll(): List<User> = userMongoRepository.findAll()
}