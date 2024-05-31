package com.oxygensend.opinions.application.request

import com.oxygensend.opinions.application.command.CreateUserCommand

data class CreateUserRequest(
    val fullName: String?,
    val thumbnailPath: String?
) {
    fun toCommand() = CreateUserCommand(
        fullName = fullName,
        thumbnailPath = thumbnailPath
    )
}
