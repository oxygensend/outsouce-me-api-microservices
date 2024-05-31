package com.oxygensend.staticdata.infrastructure.storage

import com.luciad.imageio.webp.WebPWriteParam
import com.oxygensend.commonspring.storage.StorageException
import com.oxygensend.commonspring.storage.StorageService
import com.oxygensend.staticdata.application.storage.ImageOptions
import com.oxygensend.staticdata.application.storage.ImageService
import com.oxygensend.staticdata.domain.exception.ResourceNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO

internal class ImageServiceImpl(private val storageService: StorageService, private val imageDir: String) : ImageService {
    override fun create(image: MultipartFile, options: ImageOptions): String {
        try {
            image.inputStream.use { inputStream ->
                val writeParam = WebPWriteParam(Locale.getDefault())
                writeParam.setCompressionMode(WebPWriteParam.MODE_EXPLICIT)
                writeParam.compressionType = COMPRESSION_TYPE
                writeParam.compressionQuality = options.quality
                return storageService.storeImage(ImageIO.read(inputStream), OUTPUT_FORMAT, imageDir, writeParam)
            }
        } catch (e: IOException) {
            throw StorageException("Failed to create image: " + e.message)
        }
    }

    override fun delete(vararg images: String) {
        for (image in images) {
            storageService.delete("$imageDir/$image")
        }
    }

    override fun load(image: String): Resource? {
        return try {
            storageService.load("$imageDir/$image")
        } catch (e: Exception) {
            LOGGER.error("Image not found with exception", e)
            throw ResourceNotFoundException("Image not found")
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ImageServiceImpl::class.java)
        private const val OUTPUT_FORMAT = "webp"
        private const val COMPRESSION_TYPE = "Lossy"
    }
}
