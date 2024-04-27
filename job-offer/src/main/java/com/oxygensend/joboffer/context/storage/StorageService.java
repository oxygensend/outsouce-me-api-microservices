package com.oxygensend.joboffer.context.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    /**
     * Store the file in provided subdirectory path in root dir of storage service
     */
    String store(MultipartFile file, String destinationDir);

    /**
     * Store  file in root dir of storage service
     */
    String store(MultipartFile file);

    Resource load(String filename);

    void delete(String filename);
}
