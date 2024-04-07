package com.oxygensend.opinions.context.request

import com.oxygensend.opinions.context.command.CreateUserCommand

data class CreateUserRequest(
    val fullName: String?,
    val thumbnailPath: String?
) {
    fun toCommand() = CreateUserCommand(
        fullName = fullName,
        thumbnailPath = thumbnailPath
    )
}
