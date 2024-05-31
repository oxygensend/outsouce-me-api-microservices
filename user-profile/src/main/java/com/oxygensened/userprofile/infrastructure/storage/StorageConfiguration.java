package com.oxygensened.userprofile.infrastructure.storage;

import com.oxygensend.commonspring.storage.StorageService;
import com.oxygensened.userprofile.application.storage.ThumbnailService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(StorageProperties.class)
@Configuration
public class StorageConfiguration {

    @Bean
    ThumbnailService thumbnailCreator(StorageService storageService, StorageProperties storageProperties) {
        return new ScalrThumbnailService(storageService, storageProperties.thumbnailDir());
    }
}
