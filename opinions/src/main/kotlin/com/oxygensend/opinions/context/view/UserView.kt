package com.oxygensend.opinions.context.view

import com.oxygensend.opinions.context.dto.AuthorDto
import com.oxygensend.opinions.domain.User

data class UserView(
    val id: String,
    val fullName: String?,
    val thumbnailPath: String?
) {
    companion object {
        fun from(author: String) = UserView(
            id = author,
            fullName = null,
            thumbnailPath = null
        )

        fun from(user: User) = UserView(
            id = user.id,
            fullName = user.fullName,
            thumbnailPath = user.thumbnailPath
        )

        fun from(authorDto: AuthorDto) = UserView(
            id = authorDto.id,
            fullName = authorDto.fullName,
            thumbnailPath = authorDto.thumbnailPath
        )
    }
}
