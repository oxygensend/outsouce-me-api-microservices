package com.oxygensend.staticdata.infrastructure.storage

import com.oxygensend.commonspring.storage.StorageService
import com.oxygensend.staticdata.application.storage.ImageService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(StorageProperties::class)
@Configuration
class StorageConfiguration {
    @Bean
    fun aboutUsImageService(storageService: StorageService?, storageProperties: StorageProperties): ImageService {
        return ImageServiceImpl(storageService!!, storageProperties.aboutUsImageDir!!)
    }
}
