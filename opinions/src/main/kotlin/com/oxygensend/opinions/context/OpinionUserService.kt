package com.oxygensend.opinions.context

import com.oxygensend.opinions.context.command.CreateUserCommand
import com.oxygensend.opinions.context.view.UserOpinionsDetailsView
import com.oxygensend.opinions.context.view.UserView
import com.oxygensend.opinions.domain.User
import com.oxygensend.opinions.domain.UserRepository
import com.oxygensend.opinions.domain.exception.UserNotFoundException
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class OpinionUserService(private val userRepository: UserRepository, private val opinionAggregateRepository: OpinionAggregateRepository) {

    fun createUser(toCommand: CreateUserCommand): UserView {
        val user = userRepository.save(
            User(
                id = "int-${ObjectId().toHexString()}",
                internal = true,
                fullName = toCommand.fullName,
                thumbnailPath = toCommand.thumbnailPath
            )
        )

        return UserView.from(user);
    }

    fun getUserOpinionsDetails(userId: String): UserOpinionsDetailsView {
        val user = userRepository.findById(userId) ?: throw UserNotFoundException()
        val userOpinionsDetails = opinionAggregateRepository.getUserOpinionsDetails(user)
        return UserOpinionsDetailsView(userOpinionsDetails.opinionsCount, userOpinionsDetails.opinionsRate)
    }
}