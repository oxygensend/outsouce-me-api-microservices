package com.oxygensend.joboffer.infrastructure.storage;

import com.oxygensend.joboffer.application.attachment.AttachmentService;
import com.oxygensend.joboffer.application.storage.StorageService;
import com.oxygensend.joboffer.domain.repository.AttachmentRepository;
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
    AttachmentService attachmentService(StorageProperties storageProperties, StorageService storageService,
                                        AttachmentRepository attachmentRepository) {
        return new LocalAttachmentService(storageProperties, storageService, attachmentRepository);
    }

}
