package com.oxygensend.messenger.infrastructure.jpa.repository;

import com.oxygensend.messenger.domain.MailMessage;
import org.springframework.data.jpa.repository.JpaRepository;

interface MailMessageJpaRepository extends JpaRepository<MailMessage, Long> {

}
