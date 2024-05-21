package com.oxygensend.messenger.infrastructure.jpa.repository;

import com.oxygensend.messenger.domain.MailMessage;
import com.oxygensend.messenger.domain.MailMessageRepository;
import org.springframework.stereotype.Component;

@Component
class MailMessageJpaFacadeRepository implements MailMessageRepository {

    private final MailMessageJpaRepository mailMessageJpaRepository;

    MailMessageJpaFacadeRepository(MailMessageJpaRepository mailMessageJpaRepository) {
        this.mailMessageJpaRepository = mailMessageJpaRepository;
    }

    @Override
    public MailMessage save(MailMessage mailMessage) {
        return mailMessageJpaRepository.save(mailMessage);
    }
}
