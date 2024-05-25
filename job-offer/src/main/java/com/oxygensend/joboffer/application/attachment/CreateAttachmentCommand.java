package com.oxygensend.joboffer.application.attachment;

import com.oxygensend.joboffer.domain.entity.User;
import org.springframework.web.multipart.MultipartFile;

public record CreateAttachmentCommand(User user, MultipartFile multipartFile) {
}
