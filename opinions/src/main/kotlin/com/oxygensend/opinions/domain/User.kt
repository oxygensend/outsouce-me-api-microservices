package com.oxygensend.opinions.domain

import org.springframework.data.annotation.Id

class User(
    @Id
    var id: String,
    var internal: Boolean,
    var fullName: String? = null,
    var thumbnailPath: String? = null,
)
