package com.oxygensened.userprofile.context.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ThumbnailService {

    String create(MultipartFile image, ThumbnailOptions options);

    void delete(String... thumbnails);

    Resource load(String thumbnail);

}
