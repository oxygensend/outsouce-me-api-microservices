package com.oxygensend.opinions.application.service

import com.oxygensend.opinions.application.command.CreateUserCommand
import com.oxygensend.opinions.domain.aggregate.UserOpinionsDetailsDto
import com.oxygensend.opinions.domain.User
import com.oxygensend.opinions.domain.UserRepository
import com.oxygensend.opinions.domain.aggregate.OpinionAggregateRepository
import com.oxygensend.opinions.domain.exception.UserNotFoundException
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any

@ExtendWith(MockitoExtension::class)
internal class OpinionUserServiceTest {
    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var opinionAggregateRepository: OpinionAggregateRepository

    @InjectMocks
    private lateinit var opinionUserService: OpinionUserService


    @Test
    fun createUser_expectNewUser() {
        val createUserCommand = CreateUserCommand(
            fullName = "John Doe",
            thumbnailPath = "path/to/thumbnail"
        )
        val user = User(
            id = "1",
            internal = true,
            fullName = createUserCommand.fullName,
            thumbnailPath = createUserCommand.thumbnailPath
        )
        Mockito.`when`(userRepository.save(any())).thenReturn(user)

        val userView = opinionUserService.createUser(createUserCommand)

        assertThat(userView.fullName).isEqualTo(createUserCommand.fullName)
        assertThat(userView.thumbnailPath).isEqualTo(createUserCommand.thumbnailPath)
    }

    @Test
    fun getUserOpinionsDetails_expectUserOpinionsDetails() {
        val userId = "1"
        val user = User(
            id = userId,
            internal = true,
            fullName = "John Doe",
            thumbnailPath = "path/to/thumbnail"
        )
        Mockito.`when`(userRepository.findById(userId)).thenReturn(user)
        Mockito.`when`(opinionAggregateRepository.getUserOpinionsDetails(user.id)).thenReturn(UserOpinionsDetailsDto(5, 4.5))

        val userOpinionsDetailsView = opinionUserService.getUserOpinionsDetails(userId)

        assertThat(userOpinionsDetailsView.opinionsCount).isEqualTo(5)
        assertThat(userOpinionsDetailsView.opinionsRate).isEqualTo(4.5)
    }

    @Test
    fun getUserOpinionsDetails_expectUserNotFoundException() {
        val userId = "1"
        Mockito.`when`(userRepository.findById(userId)).thenReturn(null)

        assertThrows<UserNotFoundException> { opinionUserService.getUserOpinionsDetails(userId) }
    }
}


