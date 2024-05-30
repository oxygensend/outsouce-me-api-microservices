package com.oxygensened.userprofile.infrastructure.storage;

import com.luciad.imageio.webp.WebPWriteParam;
import com.oxygensend.commonspring.storage.StorageException;
import com.oxygensend.commonspring.storage.StorageService;
import com.oxygensened.userprofile.context.storage.ThumbnailOptions;
import com.oxygensened.userprofile.context.storage.ThumbnailService;
import com.oxygensened.userprofile.domain.exception.ThumbnailNotFoundException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

final class ScalrThumbnailService implements ThumbnailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScalrThumbnailService.class);
    private static final String OUTPUT_FORMAT = "webp";
    private static final String COMPRESSION_TYPE = "Lossy";
    private final StorageService storageService;
    private final String thumbnailDir;

    ScalrThumbnailService(StorageService storageService, String thumbnailDir) {
        this.storageService = storageService;
        this.thumbnailDir = thumbnailDir;
    }

    @Override
    public String create(MultipartFile image, ThumbnailOptions options) {
        try (InputStream inputStream = image.getInputStream()) {
            BufferedImage tempImage = ImageIO.read(inputStream);
            BufferedImage resizedImage = Scalr.resize(tempImage, Scalr.Mode.FIT_EXACT, options.weight(), options.height());
            WebPWriteParam writeParam = new WebPWriteParam(Locale.getDefault());
            writeParam.setCompressionMode(WebPWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionType(COMPRESSION_TYPE);
            writeParam.setCompressionQuality(options.quality());

            return storageService.storeImage(resizedImage, OUTPUT_FORMAT, thumbnailDir, writeParam);
        } catch (IOException e) {
            throw new StorageException("Failed to create thumbnail: " + e.getMessage());
        }
    }

    @Override
    public void delete(String... thumbnails) {
        for (var thumbnail : thumbnails) {
            storageService.delete("%s/%s".formatted(thumbnailDir, thumbnail));
        }
    }

    @Override
    public Resource load(String thumbnail) {
        try {
            return storageService.load("%s/%s".formatted(thumbnailDir, thumbnail));
        } catch (Exception e) {
            LOGGER.error("Thumbnail not found with exception", e);
            throw new ThumbnailNotFoundException();
        }
    }
}
