package com.oxygensend.opinions.context.command

import com.oxygensend.opinions.config.utils.Undefined

data class UpdateOpinionCommand(
    val text: Undefined<String?>,
    val scale: Undefined<Int?>
)
