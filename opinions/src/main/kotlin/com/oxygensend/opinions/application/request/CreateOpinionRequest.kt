package com.oxygensend.opinions.application.request

import com.oxygensend.opinions.application.command.CreateOpinionCommand
import jakarta.validation.constraints.*

data class CreateOpinionRequest(
    @field:NotEmpty(message = "authorId cannot be empty")
    val authorId: String?,

    @field:NotEmpty(message = "receiverId cannot be empty")
    val receiverId: String?,

    @field:NotBlank(message = "description cannot be blank")
    @field:Size(max = 500, message = "description cannot be longer than 500 characters")
    val description: String?,

    @field:NotNull(message = "scale cannot be null")
    @field:Positive(message = "scale must be larger than 0")
    val scale: Int?
) {
    fun toCommand() = CreateOpinionCommand(
        authorId = authorId!!,
        receiverId = receiverId!!,
        text = description,
        scale = scale!!
    )
}