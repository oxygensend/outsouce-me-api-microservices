package com.oxygensend.joboffer.context.job_offer;

import com.oxygensend.commons_jdk.PagedListView;
import com.oxygensend.commons_jdk.exception.AccessDeniedException;
import com.oxygensend.commons_jdk.request_context.RequestContext;
import com.oxygensend.joboffer.context.job_offer.dto.AddressDto;
import com.oxygensend.joboffer.context.job_offer.dto.SalaryRangeDto;
import com.oxygensend.joboffer.context.job_offer.dto.command.CreateJobOfferCommand;
import com.oxygensend.joboffer.context.job_offer.dto.request.UpdateJobOfferRequest;
import com.oxygensend.joboffer.context.job_offer.dto.view.JobOfferDetailsView;
import com.oxygensend.joboffer.context.job_offer.dto.view.JobOfferManagementView;
import com.oxygensend.joboffer.context.job_offer.dto.view.JobOfferView;
import com.oxygensend.joboffer.context.job_offer.dto.view.JobOfferViewFactory;
import com.oxygensend.joboffer.context.utils.JsonNullableWrapper;
import com.oxygensend.joboffer.domain.JobOfferSearchResult;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.entity.SalaryRange;
import com.oxygensend.joboffer.domain.exception.JobOfferNotFoundException;
import com.oxygensend.joboffer.domain.exception.NoSuchUserException;
import com.oxygensend.joboffer.domain.exception.OnlyPrincipleCanPublishJobOfferException;
import com.oxygensend.joboffer.domain.repository.AddressRepository;
import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import com.oxygensend.joboffer.domain.repository.filter.JobOfferFilter;
import com.oxygensend.joboffer.domain.repository.filter.JobOfferSort;
import com.oxygensend.joboffer.domain.service.JobOffersForYou;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.function.Consumer;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.oxygensend.joboffer.context.utils.Patch.updateIfPresent;

@Service
public class JobOfferService {

    private final UserRepository userRepository;
    private final JobOfferRepository jobOfferRepository;
    private final JobOfferViewFactory jobOfferViewFactory;
    private final AddressRepository addressRepository;
    private final RequestContext requestContext;
    private final JobOffersForYou jobOffersForYou;

    public JobOfferService(UserRepository userRepository, JobOfferRepository jobOfferRepository, JobOfferViewFactory jobOfferViewFactory, AddressRepository addressRepository, RequestContext requestContext, JobOffersForYou jobOffersForYou) {
        this.userRepository = userRepository;
        this.jobOfferRepository = jobOfferRepository;
        this.jobOfferViewFactory = jobOfferViewFactory;
        this.addressRepository = addressRepository;
        this.requestContext = requestContext;
        this.jobOffersForYou = jobOffersForYou;
    }

    public JobOfferDetailsView createJobOffer(CreateJobOfferCommand command) {
        var principal = userRepository.findById(command.principalId())
                                      .orElseThrow(() -> NoSuchUserException.withId(command.principalId()));

        if (!requestContext.isUserAuthenticated(principal.id())) {
            throw new AccessDeniedException();
        }

        if (!principal.canPublishJobOffers()) {
            throw new OnlyPrincipleCanPublishJobOfferException();
        }

        var salaryRange = Optional.ofNullable(command.salaryRange()).map(SalaryRangeDto::toSalaryRange).orElse(null);
        var address = addressRepository.findByPostCodeAndCity(command.address().postCode(), command.address().city())
                                       .orElse(command.address().toAddress());
        var validTo = command.validTo() != null ? LocalDateTime.of(command.validTo(), LocalTime.NOON) : null;

        var jobOffer = JobOffer.builder()
                               .user(principal)
                               .name(command.name())
                               .description(command.description())
                               .formOfEmployment(command.formOfEmployment())
                               .experience(command.experience())
                               .salaryRange(salaryRange)
                               .address(address)
                               .technologies(command.technologies())
                               .workTypes(command.workTypes())
                               .validTo(validTo)
                               .build();

        jobOffer = jobOfferRepository.save(jobOffer);
        return jobOfferViewFactory.create(jobOffer);
    }

    public JobOfferDetailsView getJobOffer(String slug) {
        var jobOffer = jobOfferRepository.findBySlug(slug)
                                         .orElseThrow(JobOfferNotFoundException::new);

        return jobOfferViewFactory.create(jobOffer);
    }


    public void addRedirect(String slug) {
        var jobOffer = jobOfferRepository.findBySlug(slug)
                                         .orElseThrow(JobOfferNotFoundException::new);

        jobOffer.addRedirect();
        jobOfferRepository.save(jobOffer);
    }

    public void deleteJobOffer(String slug) {
        var jobOffer = jobOfferRepository.findBySlug(slug)
                                         .orElseThrow(JobOfferNotFoundException::new);

        jobOffer.setArchived(true);
        jobOfferRepository.save(jobOffer);
    }

    public JobOfferDetailsView updateJobOffer(String slug, UpdateJobOfferRequest request) {
        var jobOffer = jobOfferRepository.findBySlug(slug)
                                         .orElseThrow(JobOfferNotFoundException::new);

        if (!requestContext.isUserAuthenticated(jobOffer.user().id())) {
            throw new AccessDeniedException();
        }

        updateIfPresent(request.name(), jobOffer::setName);
        updateIfPresent(request.description(), jobOffer::setDescription);
        updateIfPresent(request.formOfEmployment(), jobOffer::setFormOfEmployment);
        updateIfPresent(request.experience(), jobOffer::setExperience);
        updateIfPresent(request.technologies(), jobOffer::setTechnologies);
        updateIfPresent(request.workTypes(), jobOffer::setWorkTypes);
        updateSalaryRange(request.salaryRange(), jobOffer);
        updateAddress(request.address(), jobOffer);
        updateValidToDate(request.validTo(), jobOffer::setValidTo);
        jobOffer.setUpdatedAt(jobOffer.updatedAt());

        jobOffer = jobOfferRepository.save(jobOffer);
        return jobOfferViewFactory.create(jobOffer);
    }

    public PagedListView<JobOfferView> getPaginatedJobOffers(JobOfferFilter filter, Pageable pageable) {
        Page<JobOfferView> page;
        if (filter.sort() == JobOfferSort.FOR_YOU) {
            if (!requestContext.isAuthorized()) {
                throw new RuntimeException();
            }

            var userId = requestContext.userId().get();
            page = jobOffersForYou.getForUser(userId, filter, pageable)
                                  .map(jobOfferViewFactory::createInfo);
        } else {
            page = jobOfferRepository.findAll(filter, pageable)
                                     .map(jobOfferViewFactory::createInfo);
        }
        return new PagedListView<>(page.getContent(), (int) page.getTotalElements(), page.getNumber() + 1, page.getTotalPages());
    }

    public JobOfferManagementView getJobOfferManagement(String slug) {
        var jobOffer = jobOfferRepository.findBySlug(slug)
                                         .orElseThrow(JobOfferNotFoundException::new);

        return jobOfferViewFactory.createJobOfferManagementView(jobOffer);
    }

    public PagedListView<JobOfferSearchResult> search(String query, Pageable pageable) {
        var paginator = jobOfferRepository.search(query, pageable);
        return new PagedListView<>(paginator.getContent(), (int) paginator.getTotalElements(), paginator.getNumber() + 1, paginator.getTotalPages());
    }


    private void updateValidToDate(JsonNullable<LocalDate> validTo, Consumer<LocalDateTime> validToSetter) {
        if (JsonNullableWrapper.isPresent(validTo)) {
            var validToUnWrapped = JsonNullableWrapper.unwrap(validTo);
            var newValue = validToUnWrapped != null ? LocalDateTime.of(validToUnWrapped, LocalTime.NOON) : null;
            validToSetter.accept(newValue);
        }
    }

    private void updateAddress(JsonNullable<AddressDto> addressDto, JobOffer jobOffer) {
        if (JsonNullableWrapper.isPresent(addressDto)) {
            var addressDtoUnwrapped = JsonNullableWrapper.unwrap(addressDto);
            if (addressDtoUnwrapped == null) {
                jobOffer.setAddress(null);
            } else {
                var address = addressRepository.findByPostCodeAndCity(addressDtoUnwrapped.postCode(), addressDtoUnwrapped.city());
                address.ifPresentOrElse(jobOffer::setAddress, () -> jobOffer.setAddress(JsonNullableWrapper.unwrap(addressDto).toAddress()));
            }
        }
    }

    private void updateSalaryRange(JsonNullable<UpdateJobOfferRequest.SalaryRangeDto> salaryRangeDto, JobOffer jobOffer) {
        if (JsonNullableWrapper.isPresent(salaryRangeDto)) {
            var salaryRangeDtoUnwrapped = JsonNullableWrapper.unwrap(salaryRangeDto);
            if (salaryRangeDtoUnwrapped == null) {
                jobOffer.setSalaryRange(null);
            } else {
                var salaryRange = Optional.ofNullable(jobOffer.salaryRange()).orElseGet(() -> {
                    var newSalaryRange = new SalaryRange();
                    jobOffer.setSalaryRange(newSalaryRange);
                    return newSalaryRange;
                });

                updateIfPresent(salaryRangeDtoUnwrapped.downRange(), salaryRange::setDownRange);
                updateIfPresent(salaryRangeDtoUnwrapped.upRange(), salaryRange::setUpRange);
                updateIfPresent(salaryRangeDtoUnwrapped.currency(), salaryRange::setCurrency);
                updateIfPresent(salaryRangeDtoUnwrapped.type(), salaryRange::setType);
            }
        }
    }
}
