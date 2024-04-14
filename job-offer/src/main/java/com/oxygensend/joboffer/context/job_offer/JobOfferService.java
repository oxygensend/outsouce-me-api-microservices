package com.oxygensend.joboffer.context.job_offer;

import com.oxygensend.joboffer.context.job_offer.dto.AddressDto;
import com.oxygensend.joboffer.context.job_offer.dto.CreateJobOfferRequest;
import com.oxygensend.joboffer.context.job_offer.dto.JobOfferView;
import com.oxygensend.joboffer.context.job_offer.dto.UpdateJobOfferRequest;
import com.oxygensend.joboffer.domain.entity.Address;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.entity.SalaryRange;
import com.oxygensend.joboffer.domain.exception.JobOfferNotFound;
import com.oxygensend.joboffer.domain.exception.NoSuchUserException;
import com.oxygensend.joboffer.domain.repository.AddressRepository;
import com.oxygensend.joboffer.domain.repository.JobOfferRepository;
import com.oxygensend.joboffer.domain.repository.UserRepository;
import com.oxygensend.joboffer.infrastructure.jackson.JsonNullableWrapper;
import java.util.function.Consumer;
import org.openapitools.jackson.nullable.JsonNullable;
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

    public JobOfferView createJobOffer(CreateJobOfferRequest request) {
        var principal = userRepository.findById(request.principalId())
                                      .orElseThrow(() -> new NoSuchUserException("Principal wih %s not found".formatted(request.principalId())));

        var salaryRange = request.salaryRange().toSalaryRange();
        var address = addressRepository.findByPostCodeAndCity(request.address().postCode(), request.address().city())
                                       .orElse(request.address().toAddress());

        var jobOffer = JobOffer.builder()
                               .user(principal)
                               .name(request.name())
                               .description(request.description())
                               .formOfEmployment(request.formOfEmployment())
                               .experience(request.experience())
                               .salaryRange(salaryRange)
                               .address(address)
                               .technologies(request.technologies())
                               .workTypes(request.workTypes())
                               .validTo(request.validTo())
                               .build();

        jobOffer = jobOfferRepository.save(jobOffer);
        return jobOfferViewFactory.create(jobOffer);
    }

    public JobOfferView getJobOffer(String slug) {
        var jobOffer = jobOfferRepository.findBySlug(slug)
                                         .orElseThrow(JobOfferNotFound::new);

        return jobOfferViewFactory.create(jobOffer);
    }


    public void addRedirect(String slug) {
        var jobOffer = jobOfferRepository.findBySlug(slug)
                                         .orElseThrow(JobOfferNotFound::new);

        jobOffer.addRedirect();
        jobOfferRepository.save(jobOffer);
    }

    public void deleteJobOffer(String slug) {
        var jobOffer = jobOfferRepository.findBySlug(slug)
                                         .orElseThrow(JobOfferNotFound::new);

        jobOfferRepository.delete(jobOffer);
    }

    public JobOfferView updateJobOffer(String slug, UpdateJobOfferRequest request) {
        var jobOffer = jobOfferRepository.findBySlug(slug)
                                         .orElseThrow(JobOfferNotFound::new);

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
