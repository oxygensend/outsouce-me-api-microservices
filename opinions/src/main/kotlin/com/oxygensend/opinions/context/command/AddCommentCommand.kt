package com.oxygensend.opinions.context.command

data class AddCommentCommand(
    val author: String,
    val text: String
)
