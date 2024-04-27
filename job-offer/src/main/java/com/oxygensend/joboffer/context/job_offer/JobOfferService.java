package com.oxygensend.joboffer.context.job_offer;

import com.oxygensend.commons_jdk.PagedListView;
import com.oxygensend.joboffer.context.job_offer.dto.AddressDto;
import com.oxygensend.joboffer.context.job_offer.dto.command.CreateJobOfferCommand;
import com.oxygensend.joboffer.context.job_offer.dto.request.CreateJobOfferRequest;
import com.oxygensend.joboffer.context.job_offer.dto.request.UpdateJobOfferRequest;
import com.oxygensend.joboffer.context.job_offer.dto.view.JobOfferDetailsView;
import com.oxygensend.joboffer.context.job_offer.dto.view.JobOfferView;
import com.oxygensend.joboffer.context.job_offer.dto.view.JobOfferViewFactory;
import com.oxygensend.joboffer.domain.repository.filter.JobOfferFilter;
import com.oxygensend.joboffer.domain.entity.Address;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.entity.SalaryRange;
import com.oxygensend.joboffer.domain.exception.JobOfferNotFoundException;
import com.oxygensend.joboffer.domain.exception.NoSuchUserException;
import com.oxygensend.joboffer.domain.exception.OnlyPrincipleCanPublishJobOfferException;
import com.oxygensend.joboffer.domain.repository.AddressRepository;
import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import com.oxygensend.joboffer.context.utils.JsonNullableWrapper;
import java.util.function.Consumer;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.oxygensend.joboffer.context.utils.Patch.updateIfPresent;

@Service
public class JobOfferService {

    private final UserRepository userRepository;
    private final JobOfferRepository jobOfferRepository;
    private final JobOfferViewFactory jobOfferViewFactory;
    private final AddressRepository addressRepository;

    public JobOfferService(UserRepository userRepository, JobOfferRepository jobOfferRepository, JobOfferViewFactory jobOfferViewFactory, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.jobOfferRepository = jobOfferRepository;
        this.jobOfferViewFactory = jobOfferViewFactory;
        this.addressRepository = addressRepository;
    }

    public JobOfferDetailsView createJobOffer(CreateJobOfferCommand command) {
        var principal = userRepository.findById(command.principalId())
                                      .orElseThrow(() -> NoSuchUserException.withId(command.principalId()));
        if (!principal.canPublishJobOffers()) {
            throw new OnlyPrincipleCanPublishJobOfferException();
        }

        var salaryRange = command.salaryRange().toSalaryRange();
        var address = addressRepository.findByPostCodeAndCity(command.address().postCode(), command.address().city())
                                       .orElse(command.address().toAddress());

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
                               .validTo(command.validTo())
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

        updateIfPresent(request.name(), jobOffer::setName);
        updateIfPresent(request.description(), jobOffer::setDescription);
        updateIfPresent(request.formOfEmployment(), jobOffer::setFormOfEmployment);
        updateIfPresent(request.experience(), jobOffer::setExperience);
        updateIfPresent(request.validTo(), jobOffer::setValidTo);
        updateIfPresent(request.technologies(), jobOffer::setTechnologies);
        updateSalaryRange(request.salaryRange(), jobOffer.salaryRange());
        updateAddress(request.address(), jobOffer::setAddress);
        jobOffer.setUpdatedAt(jobOffer.updatedAt());

        jobOffer = jobOfferRepository.save(jobOffer);
        return jobOfferViewFactory.create(jobOffer);
    }

    public PagedListView<JobOfferView> getPaginatedJobOffers(JobOfferFilter filter, Pageable pageable) {
        var page = jobOfferRepository.findAll(filter, pageable)
                                     .map(jobOfferViewFactory::createInfo);

        return new PagedListView<>(page.getContent(), page.getNumberOfElements(), page.getNumber(), page.getTotalPages());
    }

    private void updateAddress(JsonNullable<AddressDto> addressDto, Consumer<Address> addressSetter) {
        if (JsonNullableWrapper.isPresent(addressDto)) {
            var addressDtoUnwrapped = JsonNullableWrapper.unwrap(addressDto);
            var address = addressRepository.findByPostCodeAndCity(addressDtoUnwrapped.postCode(), addressDtoUnwrapped.city());
            address.ifPresentOrElse(addressSetter, () -> addressSetter.accept(JsonNullableWrapper.unwrap(addressDto).toAddress()));
        }
    }

    private void updateSalaryRange(JsonNullable<UpdateJobOfferRequest.SalaryRangeDto> salaryRangeDto, SalaryRange salaryRange) {
        if (JsonNullableWrapper.isPresent(salaryRangeDto)) {
            var salaryRangeDtoUnwrapped = JsonNullableWrapper.unwrap(salaryRangeDto);
            updateIfPresent(salaryRangeDtoUnwrapped.downRange(), salaryRange::setDownRange);
            updateIfPresent(salaryRangeDtoUnwrapped.upRange(), salaryRange::setUpRange);
            updateIfPresent(salaryRangeDtoUnwrapped.currency(), salaryRange::setCurrency);
            updateIfPresent(salaryRangeDtoUnwrapped.type(), salaryRange::setType);
        }
    }
}
