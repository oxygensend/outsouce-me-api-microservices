package com.oxygensend.joboffer.application.application;

import com.oxygensend.commonspring.PagedListView;
import com.oxygensend.commonspring.exception.AccessDeniedException;
import com.oxygensend.commonspring.request_context.RequestContext;
import com.oxygensend.joboffer.application.application.dto.CreateApplicationCommand;
import com.oxygensend.joboffer.application.application.dto.view.ApplicationInfoView;
import com.oxygensend.joboffer.application.application.dto.view.ApplicationListView;
import com.oxygensend.joboffer.application.application.dto.view.ApplicationView;
import com.oxygensend.joboffer.application.application.dto.view.ApplicationViewFactory;
import com.oxygensend.joboffer.application.attachment.AttachmentService;
import com.oxygensend.joboffer.application.attachment.CreateAttachmentCommand;
import com.oxygensend.joboffer.application.cache.CacheData;
import com.oxygensend.joboffer.application.cache.event.ClearCacheEvent;
import com.oxygensend.joboffer.application.notifications.NotificationsService;
import com.oxygensend.joboffer.domain.entity.Application;
import com.oxygensend.joboffer.domain.entity.part.ApplicationStatus;
import com.oxygensend.joboffer.domain.exception.ApplicationAlreadyExistsException;
import com.oxygensend.joboffer.domain.exception.ApplicationNotFoundException;
import com.oxygensend.joboffer.domain.exception.NoSuchJobOfferException;
import com.oxygensend.joboffer.domain.exception.NoSuchUserException;
import com.oxygensend.joboffer.domain.exception.OnlyDeveloperCanApplyForJobOfferException;
import com.oxygensend.joboffer.domain.repository.ApplicationRepository;
import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import com.oxygensend.joboffer.domain.repository.filter.ApplicationFilter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ApplicationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationService.class);
    private final UserRepository userRepository;
    private final JobOfferRepository jobOfferRepository;
    private final ApplicationRepository applicationRepository;
    private final AttachmentService attachmentService;
    private final ApplicationViewFactory applicationViewFactory;
    private final NotificationsService notificationsService;
    private final RequestContext requestContext;
    private final ApplicationEventPublisher applicationEventPublisher;

    ApplicationService(UserRepository userRepository, JobOfferRepository jobOfferRepository,
                       ApplicationRepository applicationRepository,
                       AttachmentService attachmentService, ApplicationViewFactory applicationViewFactory,
                       NotificationsService notificationsService, RequestContext requestContext,
                       ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.jobOfferRepository = jobOfferRepository;
        this.applicationRepository = applicationRepository;
        this.attachmentService = attachmentService;
        this.applicationViewFactory = applicationViewFactory;
        this.notificationsService = notificationsService;
        this.requestContext = requestContext;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public void createApplication(CreateApplicationCommand command) {
        var start = Instant.now().toEpochMilli();
        var user = userRepository.findById(command.userId())
                                 .orElseThrow(() -> NoSuchUserException.withId(command.userId()));

        if (!requestContext.isUserAuthenticated(user.id())) {
            throw new AccessDeniedException();
        }

        if (!user.canApplyForJobOffers()) {
            throw new OnlyDeveloperCanApplyForJobOfferException();
        }
        var downloadUser = Instant.now().toEpochMilli();
        LOGGER.info("Downloaded user {}", downloadUser - start);

        var jobOffer = jobOfferRepository.findById(command.jobOfferId())
                                         .orElseThrow(() -> NoSuchJobOfferException.withId(command.jobOfferId()));

        var downloadJobOffer = Instant.now().toEpochMilli();
        LOGGER.info("Downloaded jobOffer {}", downloadJobOffer - downloadUser);

        if (applicationRepository.existsByJobOfferAndUser(jobOffer, user)) {
            throw new ApplicationAlreadyExistsException();
        }

        var downloadApplication = Instant.now().toEpochMilli();
        LOGGER.info("Downloaded jobOffer {}", downloadApplication - downloadJobOffer);

        var application = new Application(user, jobOffer, command.description());
        storeAttachments(command.attachments(), application);
        jobOffer.increaseNumberOfApplications();

        var storeAttachment = Instant.now().toEpochMilli();
        LOGGER.info("Store attachment {}", storeAttachment - downloadApplication);

        applicationRepository.save(application);
        var saveAttachment = Instant.now().toEpochMilli();
        LOGGER.info("Save attachment {}", saveAttachment - storeAttachment);

        notificationsService.sendJobOfferApplicationNotifications(application);

        var kafka = Instant.now().toEpochMilli();
        LOGGER.info("Send notification KAFKA {}", kafka - saveAttachment);
        LOGGER.info("End date {}", start - Instant.now().toEpochMilli());
    }

    @Transactional
    public void deleteApplication(Long id) {
        var application = applicationRepository.findById(id).orElseThrow(ApplicationNotFoundException::new);
        application.setDeleted(true);

        applicationRepository.save(application);
        applicationEventPublisher.publishEvent(
            new ClearCacheEvent(CacheData.APPLICATION_CACHE, id.toString(), application.user().id()));
    }

    public void changeStatus(Long id, ApplicationStatus status) {
        var application = applicationRepository.findById(id).orElseThrow(ApplicationNotFoundException::new);
        application.setStatus(status);

        applicationRepository.save(application);
    }

    public ApplicationView getApplication(Long id) {
        return applicationRepository.findById(id)
                                    .map(applicationViewFactory::create)
                                    .orElseThrow(ApplicationNotFoundException::new);

    }

    public PagedListView<ApplicationView> getApplications(ApplicationFilter filter, Pageable pageable) {
        var paginator = applicationRepository.findAll(filter, pageable)
                                             .map(applicationViewFactory::create);

        return new PagedListView<>(paginator.getContent(), (int) paginator.getTotalElements(),
                                   paginator.getNumber() + 1, paginator.getTotalPages());
    }

    public PagedListView<ApplicationListView> getApplicationsByUser(ApplicationFilter filter, Pageable pageable) {
        var paginator = applicationRepository.findAll(filter, pageable)
                                             .map(applicationViewFactory::createListView);
        return new PagedListView<>(paginator.getContent(), paginator.getNumberOfElements(), paginator.getNumber(),
                                   paginator.getTotalPages());
    }

    public ApplicationInfoView getApplicationInfo(Long id) {
        return applicationRepository.findById(id)
                                    .map(applicationViewFactory::createInfoView)
                                    .orElseThrow(ApplicationNotFoundException::new);
    }

    private void storeAttachments(List<MultipartFile> attachments, Application application) {
        Stream.ofNullable(attachments)
              .flatMap(List::stream)
              .map(file -> new CreateAttachmentCommand(application.user(), file))
              .map(attachmentService::createAttachment)
              .forEach(application::addAttachment);
    }
}
