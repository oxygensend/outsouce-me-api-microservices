package com.oxygensend.opinions.application.command

import com.oxygensend.opinions.application.utils.Undefined

data class UpdateOpinionCommand(
    val text: Undefined<String?>,
    val scale: Undefined<Int?>
)
