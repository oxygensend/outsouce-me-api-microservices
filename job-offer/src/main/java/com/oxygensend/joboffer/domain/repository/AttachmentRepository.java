package com.oxygensend.joboffer.domain.repository;

import com.oxygensend.joboffer.domain.entity.Attachment;
import java.util.Optional;

public interface AttachmentRepository {
    Optional<Attachment> findById(Long id);
}
