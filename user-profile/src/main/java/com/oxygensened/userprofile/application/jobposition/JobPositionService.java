package com.oxygensened.userprofile.application.jobposition;

import com.oxygensend.commonspring.request_context.RequestContext;
import com.oxygensened.userprofile.application.cache.event.ClearCacheEvent;
import com.oxygensened.userprofile.application.cache.event.ClearListCacheEvent;
import com.oxygensened.userprofile.application.company.CompanyService;
import com.oxygensened.userprofile.application.jobposition.dto.request.CreateJobPositionRequest;
import com.oxygensened.userprofile.application.jobposition.dto.request.UpdateJobPositionRequest;
import com.oxygensened.userprofile.application.jobposition.dto.view.JobPositionView;
import com.oxygensened.userprofile.domain.entity.JobPosition;
import com.oxygensend.commonspring.exception.AccessDeniedException;
import com.oxygensened.userprofile.domain.exception.JobPositionNotFoundException;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.domain.repository.JobPositionRepository;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import com.oxygensened.userprofile.domain.service.DomainUserService;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import static com.oxygensened.userprofile.application.utils.Patch.updateIfPresent;

@Service
public class JobPositionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobPositionService.class);
    // TODO Develop the process of changing active job
    private final UserRepository userRepository;
    private final JobPositionRepository jobPositionRepository;
    private final CompanyService companyService;
    private final DomainUserService domainUserService;
    private final RequestContext requestContext;
    private final ApplicationEventPublisher applicationEventPublisher;

    public JobPositionService(UserRepository userRepository, JobPositionRepository jobPositionRepository, CompanyService companyService, DomainUserService domainUserService, RequestContext requestContext, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.jobPositionRepository = jobPositionRepository;
        this.companyService = companyService;
        this.domainUserService = domainUserService;
        this.requestContext = requestContext;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public JobPositionView createJobPosition(Long userId, CreateJobPositionRequest request) {
        var user = userRepository.findById(userId).orElseThrow(() -> UserNotFoundException.withId(userId));
        if (!requestContext.isUserAuthenticated(userId)) {
            LOGGER.info("User {} is not allow to create job position for different entities", requestContext.userIdAsString());
            throw new AccessDeniedException();
        }


        var company = companyService.getCompany(request.companyName());

        var jobPosition = JobPosition.builder()
                                     .name(request.name())
                                     .formOfEmployment(request.formOfEmployment())
                                     .company(company)
                                     .description(request.description())
                                     .startDate(request.startDate())
                                     .endDate(request.endDate())
                                     .individual(user)
                                     .build();

        domainUserService.changeActiveJobPosition(user, jobPosition);
        jobPosition = jobPositionRepository.save(jobPosition);
        applicationEventPublisher.publishEvent(ClearCacheEvent.jobPosition(userId));
        return JobPositionView.from(jobPosition);
    }

    public JobPositionView updateJobPosition(Long userId, Long jobPositionId, UpdateJobPositionRequest request) {
        var jobPosition = jobPositionRepository.findByIdAndIndividualId(jobPositionId, userId)
                                               .orElseThrow(() -> JobPositionNotFoundException.withId(jobPositionId));

        if (!requestContext.isUserAuthenticated(userId)) {
            LOGGER.info("User {} is not allow to update job position for different entities", requestContext.userIdAsString());
            throw new AccessDeniedException();
        }

        updateIfPresent(request.name(), jobPosition::setName);
        updateIfPresent(request.formOfEmployment(), jobPosition::setFormOfEmployment);
        updateIfPresent(request.description(), jobPosition::setDescription);
        updateIfPresent(request.startDate(), jobPosition::setStartDate);
        updateIfPresent(request.endDate(), jobPosition::setEndDate);
        updateCompany(request.companyName(), jobPosition);

        domainUserService.changeActiveJobPosition(jobPosition.individual(), jobPosition);
        jobPosition = jobPositionRepository.save(jobPosition);
        applicationEventPublisher.publishEvent(ClearCacheEvent.jobPosition(userId));
        return JobPositionView.from(jobPosition);
    }

    public void deleteJobPosition(Long userId, Long jobPositionId) {
        var jobPosition = jobPositionRepository.findByIdAndIndividualId(jobPositionId, userId)
                                               .orElseThrow(() -> JobPositionNotFoundException.withId(jobPositionId));

        if (!requestContext.isUserAuthenticated(userId)) {
            LOGGER.info("User {} is not allow to delete job position for different entities", requestContext.userIdAsString());
            throw new AccessDeniedException();
        }

        jobPosition.individual().setActiveJobPosition(null);
        jobPositionRepository.delete(jobPosition);
        applicationEventPublisher.publishEvent(ClearCacheEvent.jobPosition(userId));
    }

    public List<JobPositionView> getJobPositions(Long userId) {
        return jobPositionRepository.findAllByIndividualId(userId)
                                    .stream()
                                    .map(JobPositionView::from)
                                    .toList();
    }

    private void updateCompany(JsonNullable<String> companyNameNullable, JobPosition jobPosition) {
        if (!companyNameNullable.isPresent()) {
            return;
        }

        var companyName = companyNameNullable.get();
        var company = companyService.getCompany(companyName);
        jobPosition.setCompany(company);
    }
}
