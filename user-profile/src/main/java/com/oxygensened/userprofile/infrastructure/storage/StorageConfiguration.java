package com.oxygensened.userprofile.infrastructure.storage;

import com.oxygensened.userprofile.config.properties.StorageProperties;
import com.oxygensened.userprofile.context.storage.StorageService;
import com.oxygensened.userprofile.context.storage.ThumbnailService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(StorageProperties.class)
@Configuration
public class StorageConfiguration {

    @Bean
    StorageService imagesStoreService(StorageProperties storageProperties, FileSystem fileSystem) {
        return new LocalStorageService(storageProperties.rootLocation(), fileSystem);
    }

    @Bean
    ThumbnailService thumbnailCreator(StorageService storageService, StorageProperties storageProperties) {
        return new ThumbnailsThumbnailCreator(storageService, storageProperties.thumbnailDir());
    }
}
