package com.oxygensend.staticdata.application.storage

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

interface ImageService {
    fun create(image: MultipartFile, options: ImageOptions = ImageOptions()): String
    fun delete(vararg images: String)
    fun load(image: String): Resource?
}
