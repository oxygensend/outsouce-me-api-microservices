package com.oxygensend.joboffer.infrastructure.jpa;

import com.oxygensend.joboffer.domain.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

interface AttachmentJpaRepository extends JpaRepository<Attachment, Long> {
}
