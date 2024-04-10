package com.oxygensend.opinions.context.request

import com.oxygensend.opinions.context.command.AddCommentCommand
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class AddCommentRequest(
    @field:NotEmpty(message = "author cannot be empty")
    val author: String,

    @field:NotEmpty(message = "text cannot be empty")
    @field:Size(max = 128, message = "text cannot be longer than 255 characters")
    val text: String
) {

    fun toCommand() = AddCommentCommand(
        author = author,
        text = text
    )

}
