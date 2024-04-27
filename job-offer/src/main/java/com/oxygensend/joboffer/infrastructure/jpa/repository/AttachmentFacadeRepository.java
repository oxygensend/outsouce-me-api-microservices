package com.oxygensend.joboffer.infrastructure.jpa.repository;

import com.oxygensend.joboffer.domain.entity.Attachment;
import com.oxygensend.joboffer.domain.repository.AttachmentRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
final class AttachmentFacadeRepository implements AttachmentRepository {

    private final AttachmentJpaRepository attachmentJpaRepository;

    AttachmentFacadeRepository(AttachmentJpaRepository attachmentJpaRepository) {
        this.attachmentJpaRepository = attachmentJpaRepository;
    }

    @Override
    public Optional<Attachment> findById(Long id) {
        return attachmentJpaRepository.findById(id);
    }
}
