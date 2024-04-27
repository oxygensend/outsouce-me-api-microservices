package com.oxygensend.joboffer.context.application;

import com.oxygensend.commons_jdk.PagedListView;
import com.oxygensend.joboffer.context.application.dto.ApplicationFilter;
import com.oxygensend.joboffer.context.application.dto.CreateApplicationCommand;
import com.oxygensend.joboffer.context.application.view.ApplicationListView;
import com.oxygensend.joboffer.context.application.view.ApplicationView;
import com.oxygensend.joboffer.context.application.view.ApplicationViewFactory;
import com.oxygensend.joboffer.context.attachment.AttachmentService;
import com.oxygensend.joboffer.context.attachment.CreateAttachmentCommand;
import com.oxygensend.joboffer.context.notifications.NotificationsService;
import com.oxygensend.joboffer.domain.ApplicationStatus;
import com.oxygensend.joboffer.domain.entity.Application;
import com.oxygensend.joboffer.domain.exception.ApplicationAlreadyExistsException;
import com.oxygensend.joboffer.domain.exception.ApplicationNotFoundException;
import com.oxygensend.joboffer.domain.exception.NoSuchJobOfferException;
import com.oxygensend.joboffer.domain.exception.NoSuchUserException;
import com.oxygensend.joboffer.domain.exception.OnlyDeveloperCanApplyForJobOfferException;
import com.oxygensend.joboffer.domain.repository.ApplicationRepository;
import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ApplicationService {
    private final UserRepository userRepository;
    private final JobOfferRepository jobOfferRepository;
    private final ApplicationRepository applicationRepository;
    private final AttachmentService attachmentService;
    private final ApplicationViewFactory applicationViewFactory;
    private final NotificationsService notificationsService;

    ApplicationService(UserRepository userRepository, JobOfferRepository jobOfferRepository, ApplicationRepository applicationRepository,
                       AttachmentService attachmentService, ApplicationViewFactory applicationViewFactory, NotificationsService notificationsService) {
        this.userRepository = userRepository;
        this.jobOfferRepository = jobOfferRepository;
        this.applicationRepository = applicationRepository;
        this.attachmentService = attachmentService;
        this.applicationViewFactory = applicationViewFactory;
        this.notificationsService = notificationsService;
    }

    public void createApplication(CreateApplicationCommand command) {
        var user = userRepository.findById(command.userId()).orElseThrow(() -> NoSuchUserException.withId(command.userId()));
        if (!user.canApplyForJobOffers()) {
            throw new OnlyDeveloperCanApplyForJobOfferException();
        }

        var jobOffer = jobOfferRepository.findById(command.jobOfferId()).orElseThrow(() -> NoSuchJobOfferException.withId(command.jobOfferId()));

        if (applicationRepository.existsByJobOfferAndUser(jobOffer, user)) {
            throw new ApplicationAlreadyExistsException();
        }

        var application = new Application(user, jobOffer, command.description());
        storeAttachments(command.attachments(), application);
        jobOffer.increaseNumberOfApplications();

        applicationRepository.save(application);
        notificationsService.sendJobOfferApplicationNotifications(application);
    }

    public void deleteApplication(Long id) {
        var application = applicationRepository.findById(id).orElseThrow(ApplicationNotFoundException::new);
        application.setDeleted(true);

        applicationRepository.save(application);
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

    public PagedListView<ApplicationListView> getApplicationsByUser(ApplicationFilter filter, Pageable pageable) {
        var paginator = applicationRepository.findAll(filter, pageable)
                                             .map(applicationViewFactory::createListView);
        return new PagedListView<>(paginator.getContent(), paginator.getNumberOfElements(), paginator.getNumber(), paginator.getTotalPages());
    }

    private void storeAttachments(List<MultipartFile> attachments, Application application) {
        Stream.ofNullable(attachments)
              .flatMap(List::stream)
              .map(file -> new CreateAttachmentCommand(application.user(), file))
              .map(attachmentService::createAttachment)
              .forEach(application::addAttachment);
    }
}
