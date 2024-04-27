package com.oxygensend.joboffer.context.attachment;

import com.oxygensend.joboffer.config.properties.StorageProperties;
import com.oxygensend.joboffer.context.storage.StorageService;
import com.oxygensend.joboffer.domain.entity.Attachment;
import com.oxygensend.joboffer.domain.exception.AttachmentNotFound;
import com.oxygensend.joboffer.domain.repository.AttachmentRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class AttachmentService {
    private final String attachmentLocation;
    private final StorageService storageService;
    private final AttachmentRepository attachmentRepository;

    AttachmentService(StorageProperties properties, StorageService storageService, AttachmentRepository attachmentRepository) {
        this.attachmentLocation = properties.attachmentsLocation();
        this.storageService = storageService;
        this.attachmentRepository = attachmentRepository;
    }

    public Attachment createAttachment(CreateAttachmentCommand command) {
        var multipartFile = command.multipartFile();
        var originalFileName = multipartFile.getOriginalFilename();
        var size = multipartFile.getSize();
        var fileName = storageService.store(multipartFile, attachmentLocation);

        return new Attachment(originalFileName, fileName, size, command.user());
    }

    public Resource downloadAttachment(Long id) {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(AttachmentNotFound::new);
        return storageService.load(attachmentLocation + "/" + attachment.name());
    }
}
