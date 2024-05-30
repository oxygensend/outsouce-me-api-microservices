package com.oxygensend.messenger.application.mail;

import com.oxygensend.commonspring.exception.AccessDeniedException;
import com.oxygensend.commonspring.request_context.RequestContext;
import com.oxygensend.messenger.application.mail.dto.MailMessageRequest;
import com.oxygensend.messenger.application.mail.dto.SendMailCommand;
import com.oxygensend.messenger.application.notifications.NotificationsService;
import com.oxygensend.messenger.domain.MailMessage;
import com.oxygensend.messenger.domain.MailMessageRepository;
import com.oxygensend.messenger.domain.UserRepository;
import com.oxygensend.messenger.domain.exception.NoSuchUserException;
import com.oxygensend.messenger.domain.exception.SameUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MailMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailMessageService.class);
    private static final int MAX_RETRIES = 3;
    private final MailService mailService;
    private final MailMessageQueue queue;
    private final MailMessageRepository repository;
    private final UserRepository userRepository;
    private final NotificationsService notificationsService;
    private final RequestContext requestContext;

    MailMessageService(MailService mailService, MailMessageQueue queue, MailMessageRepository repository, UserRepository userRepository, NotificationsService notificationsService, RequestContext requestContext) {
        this.mailService = mailService;
        this.queue = queue;
        this.repository = repository;
        this.userRepository = userRepository;
        this.notificationsService = notificationsService;
        this.requestContext = requestContext;
    }

    public void sendMailMessage(MailMessageRequest request) {
        if (!requestContext.isUserAuthenticated(request.senderId())) {
            throw new AccessDeniedException();
        }

        if (request.senderId().equals(request.recipientId())) {
            throw new SameUserException("Sender and recipient cannot be the same");
        }

        var sender = userRepository.findById(request.senderId()).orElseThrow(() -> NoSuchUserException.withId(request.senderId()));
        var recipient = userRepository.findById(request.recipientId()).orElseThrow(() -> NoSuchUserException.withId(request.recipientId()));

        var command = new SendMailCommand(sender, recipient, request.subject(), request.body());
        queue.bookmark(() -> send(command));
    }

    private void send(SendMailCommand command) {
        send(command, 0);
    }

    private void send(SendMailCommand command, int retries) {
        boolean status = mailService.send(command);
        if (!status && retries < MAX_RETRIES) {
            send(command, retries + 1);
        } else if (!status) {
            LOGGER.error("Failed to send mail message to {}  from {}", command.to().email(), command.from().email());
        } else {
            LOGGER.info("Mail message sent to {} from {}", command.to(), command.from());
            var mailMessage = new MailMessage(command.subject(), command.body(), command.to(), command.from());
            repository.save(mailMessage);
            notificationsService.sendInternalMessageInformingAboutMailMessageDelivery(mailMessage);
        }

    }
}
