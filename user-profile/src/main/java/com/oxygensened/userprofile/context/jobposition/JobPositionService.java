package com.oxygensened.userprofile.context.jobposition;

import com.oxygensend.commons_jdk.request_context.RequestContext;
import com.oxygensened.userprofile.context.company.CompanyService;
import com.oxygensened.userprofile.context.jobposition.dto.request.CreateJobPositionRequest;
import com.oxygensened.userprofile.context.jobposition.dto.request.UpdateJobPositionRequest;
import com.oxygensened.userprofile.context.jobposition.dto.view.JobPositionView;
import com.oxygensened.userprofile.domain.entity.JobPosition;
import com.oxygensend.commons_jdk.exception.AccessDeniedException;
import com.oxygensened.userprofile.domain.exception.JobPositionNotFoundException;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.domain.repository.JobPositionRepository;
import com.oxygensened.userprofile.domain.repository.UserRepository;
import com.oxygensened.userprofile.domain.service.DomainUserService;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.oxygensened.userprofile.context.utils.Patch.updateIfPresent;

@Service
public class JobPositionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobPositionService.class);
    // TODO Develop the process of changing active job
    private final UserRepository userRepository;
    private final JobPositionRepository jobPositionRepository;
    private final CompanyService companyService;
    private final DomainUserService domainUserService;
    private final RequestContext requestContext;

    public JobPositionService(UserRepository userRepository, JobPositionRepository jobPositionRepository, CompanyService companyService, DomainUserService domainUserService, RequestContext requestContext) {
        this.userRepository = userRepository;
        this.jobPositionRepository = jobPositionRepository;
        this.companyService = companyService;
        this.domainUserService = domainUserService;
        this.requestContext = requestContext;
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
