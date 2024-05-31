package com.oxygensend.opinions.application.command

data class AddCommentCommand(
    val author: String,
    val text: String
)
