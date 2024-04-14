package com.oxygensened.userprofile.context.jobposition;

import com.oxygensened.userprofile.context.company.CompanyService;
import com.oxygensened.userprofile.context.jobposition.dto.CreateJobPositionRequest;
import com.oxygensened.userprofile.context.jobposition.dto.JobPositionView;
import com.oxygensened.userprofile.context.jobposition.dto.UpdateJobPositionRequest;
import com.oxygensened.userprofile.domain.JobPosition;
import com.oxygensened.userprofile.domain.JobPositionRepository;
import com.oxygensened.userprofile.domain.UserRepository;
import com.oxygensened.userprofile.domain.exception.JobPositionNotFoundException;
import com.oxygensened.userprofile.domain.exception.UserNotFoundException;
import com.oxygensened.userprofile.domain.service.DomainUserService;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Service;

import static com.oxygensened.userprofile.context.utils.Patch.updateIfPresent;

@Service
public class JobPositionService {

    // TODO Develop the process of changing active job
    private final UserRepository userRepository;
    private final JobPositionRepository jobPositionRepository;
    private final CompanyService companyService;
    private final DomainUserService domainUserService;

    public JobPositionService(UserRepository userRepository, JobPositionRepository jobPositionRepository, CompanyService companyService, DomainUserService domainUserService) {
        this.userRepository = userRepository;
        this.jobPositionRepository = jobPositionRepository;
        this.companyService = companyService;
        this.domainUserService = domainUserService;
    }

    public JobPositionView createJobPosition(Long userId, CreateJobPositionRequest request) {
        var user = userRepository.findById(userId).orElseThrow(() -> UserNotFoundException.withId(userId));
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
