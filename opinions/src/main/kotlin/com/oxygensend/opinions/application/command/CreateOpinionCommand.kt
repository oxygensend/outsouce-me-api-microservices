package com.oxygensend.opinions.application.command

data class CreateOpinionCommand(
    val authorId: String,
    val receiverId: String,
    val text: String?,
    val scale: Int
)