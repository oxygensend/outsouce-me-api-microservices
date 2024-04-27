package com.oxygensend.joboffer.context.attachment;

import com.oxygensend.joboffer.domain.entity.Attachment;
import org.springframework.core.io.Resource;

public interface AttachmentService {
    Attachment createAttachment(CreateAttachmentCommand command);

    Resource downloadAttachment(Long id);
}
