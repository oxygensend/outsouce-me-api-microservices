package com.oxygensend.opinions.domain

import org.bson.types.ObjectId

interface OpinionRepository {

    fun save(opinion: Opinion): Opinion

    fun findById(id: ObjectId): Opinion?

    fun delete(opinion: Opinion)

    fun existsByAuthorAndReceiver(author: String, receiver: String): Boolean
    fun count(): Long
    fun saveAll(opinions: List<Opinion>)
}